package br.org.ons.platform.bus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;
import com.ibm.broker.plugin.MbUserException;

public class CommandRouter_SelectValidVersion extends MbJavaComputeNode {

	public void evaluate(MbMessageAssembly inAssembly) throws MbException {
		MbOutputTerminal out = getOutputTerminal("out");

		MbMessage inMessage = inAssembly.getMessage();
		MbMessageAssembly outAssembly = null;
		try {
			// ----------------------------------------------------------
			// Add user code below
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			

			// Obtendo data do comando a partir da mensagem JSON
			MbElement timelineDate = inMessage.getRootElement()
					.getFirstElementByPath("/JSON/Data/metadata/timelineDate");
			
			Date dtRef = sdf.parse(timelineDate.getValueAsString().substring(0,10));

			@SuppressWarnings("unchecked")
			// Buscando as Service Versions vigentes na data do comando
			
			List<MbElement> validVersions = new ArrayList();
			
			MbElement[] items = inMessage.getRootElement().getAllElementsByPath("LookupResponse/Data/Item");
			
			
			for(MbElement item : items) {
				MbElement[] props = item.getAllElementsByPath("properties/Item");
				Date dtInicio = null;
				Date dtFim = null;
				for(MbElement prop : props) {
					if(prop.getFirstElementByPath("name").getValueAsString().equals("gep63_versionAvailabilityDate")) {
						dtInicio = sdf.parse(prop.getFirstElementByPath("value").getValueAsString());
					}
					if(prop.getFirstElementByPath("name").getValueAsString().equals("gep63_versionTerminationDate")) {
						dtFim = sdf.parse(prop.getFirstElementByPath("value").getValueAsString());
					}
				}
				if(dtRef.after(dtInicio) && dtRef.before(dtFim)) {
					validVersions.add(item);
				}
			}
//			List<MbElement> validVersions = (List<MbElement>) inMessage
//					.getRootElement()
//					.evaluateXPath(
//							"LookupResponse/Data/Item[concat(translate(properties/Item[name='gep63_versionAvailabilityDate']/value, '-/T:Z', ''), '000000') <= translate('"
//									+ timelineDate.getValueAsString()
//									+ "', '-/T:Z', '') and concat(translate(properties/Item[name='gep63_versionTerminationDate']/value, '-/T:Z', ''), '235959') > translate('"
//									+ timelineDate.getValueAsString()
//									+ "', '-/T:Z', '')]");
			if (validVersions == null || validVersions.size() == 0) {
				// Caso n�o encontre nenhuma Service Version vigente, lan�a exce��o
				throw new MbUserException(this, "evaluate()", "", "",
						"[ONS] Nenhuma vers�o de servi�o vigente na data do comando", null);
			} else if (validVersions.size() == 1) {
				// Obtendo o bsrURI da Service Version vigente
				String bsrURI = validVersions.get(0)
						.getFirstElementByPath("bsrURI").getValueAsString();

				// Montando a query de busca XPath (busca a URL do Endpoint para
				// a Service Version vigente)
				String query = "/WSRR/8.5/Metadata/JSON/PropertyQuery?query=/WSRR/GenericObject[@bsrURI=%27" +
						bsrURI +
						"%27]/gep63_provides(.)/gep63_availableEndpoints(.)&p1=rest80_baseURL";

				// Setando a query de busca na URI do HTTPRequest
				MbMessage environment = new MbMessage(
						inAssembly.getLocalEnvironment());
				MbElement environmentRoot = environment.getRootElement();
				environmentRoot
						.evaluateXPath("?Destination/?HTTP/?RequestLine/?RequestURI[set-value('"
								+ query + "')]");

				// Obtendo nome e vers�o da Service Version vigente e setando no Metadata do comando
				@SuppressWarnings("unchecked")
				String name = ((List<MbElement>) validVersions.get(0)
						.evaluateXPath("properties/Item[name='name']/value")).get(0).getValueAsString();
				@SuppressWarnings("unchecked")
				String version = ((List<MbElement>) validVersions.get(0)
						.evaluateXPath("properties/Item[name='version']/value")).get(0).getValueAsString();
				inMessage.getRootElement().evaluateXPath(
						"JSON/Data/?metadata/?handlerName[set-value('" + name + "')]");
				inMessage.getRootElement().evaluateXPath(
						"JSON/Data/?metadata/?handlerVersion[set-value('" + version + "')]");

				// Criando o message assembly para retorno
				outAssembly = new MbMessageAssembly(inAssembly, environment,
						inAssembly.getExceptionList(), inMessage);
			} else {
				// Caso encontre mais de uma Service Version vigente na data do
				// comando, lan�a exce��o
				throw new MbUserException(this, "evaluate()", "", "",
						"[ONS] M�ltiplas vers�es de servi�o vigentes na data do comando", null);
			}

			// End of user code
			// ----------------------------------------------------------
		} catch (MbException e) {
			// Re-throw to allow Broker handling of MbException
			throw e;
		} catch (RuntimeException e) {
			// Re-throw to allow Broker handling of RuntimeException
			throw e;
		} catch (Exception e) {
			// Consider replacing Exception with type(s) thrown by user code
			// Example handling ensures all exceptions are re-thrown to be
			// handled in the flow
			throw new MbUserException(this, "evaluate()", "", "", e.toString(),
					null);
		}
		// The following should only be changed
		// if not propagating message to the 'out' terminal
		out.propagate(outAssembly);
	}
}
