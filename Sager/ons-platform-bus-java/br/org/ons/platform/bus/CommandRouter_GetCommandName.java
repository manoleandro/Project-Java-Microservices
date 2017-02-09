package br.org.ons.platform.bus;

import com.ibm.broker.config.proxy.BrokerProxy;
import com.ibm.broker.config.proxy.ConfigurableService;
import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.javastartparameters.JavaStartParameters;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbJSON;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;
import com.ibm.broker.plugin.MbUserException;

public class CommandRouter_GetCommandName extends MbJavaComputeNode {
	public void evaluate(MbMessageAssembly inAssembly) throws MbException {
		MbOutputTerminal out = getOutputTerminal("out");

		MbMessage inMessage = inAssembly.getMessage();
		MbMessageAssembly outAssembly = null;
		try {
			// ----------------------------------------------------------
			// Add user code below

			MbMessage outMessage = new MbMessage(inMessage);
			
			String[] auth = JavaStartParameters.getResourceUserAndPassword("DefaultWSRR::", "WSRR", "DefaultWSRR");
			
			MbElement properties = outMessage.getRootElement().getFirstElementByPath("Properties");
			properties.getFirstElementByPath("IdentitySourceType").setValue("usernameAndPassword");
			properties.getFirstElementByPath("IdentitySourceToken").setValue(auth[0]);
			properties.getFirstElementByPath("IdentitySourcePassword").setValue(auth[1]);
			
			
			// Obtendo URL do WSRR a partir do Configurable Service do
			// Broker e setando na URI do HTTPRequest
			BrokerProxy bp = BrokerProxy.getLocalInstance();
			while (!bp.hasBeenPopulatedByBroker()) {
			}
			ConfigurableService defaultWSRR = bp.getConfigurableService(
					"ServiceRegistries", "DefaultWSRR");
			
			String wsrrUrl = defaultWSRR.getProperties().getProperty(
					"endpointAddress");
			MbMessage environment = new MbMessage(
					inAssembly.getLocalEnvironment());
			MbElement environmentRoot = environment.getRootElement();
			environmentRoot
					.evaluateXPath("?Destination/?HTTP/?RequestURL[set-value('"
							+ wsrrUrl + "')]");

			// Obtendo nome e namespace do comando a partir da mensagem JSON
			MbElement commandName = outMessage.getRootElement()
					.getFirstElementByPath("/JSON/Data/command/name");
			if (commandName == null || commandName.getValue() == null) {
				// Lan�a exce��o caso n�o encontre nome do comando
				throw new MbUserException(this, "evaluate()", "", "",
						"[ONS] Command name not specified", null);
			}
			String commandNameValue = commandName.getValueAsString();
			String operationNamespace = commandNameValue.substring(0,
					commandNameValue.lastIndexOf('.'));
			String operationName = commandNameValue.substring(commandNameValue
					.lastIndexOf('.') + 1);

			// Montando a query de busca XPath (busca as Service Versions que
			// prov�m uma determinada Operation)
			String query = "/WSRR/8.5/Metadata/JSON/GraphQuery?query="
					+ "/WSRR/GenericObject[gep63_provides(.)[gep63_serviceInterface(.)[sm63_operations(.)[@name=%27"
					+ operationName + "%27%20and%20@namespace=%27"
					+ operationNamespace + "%27]]]]";

			// Setando a query de busca na URI do HTTPRequest
			environmentRoot
					.evaluateXPath("?Destination/?HTTP/?RequestLine/?RequestURI[set-value('"
							+ query + "')]");

			// Setando mensagem vazia para o HTTP GET
			outMessage.getRootElement().createElementAsLastChild(MbJSON.OBJECT,
					"LookupRequest", null);
			outMessage.getRootElement().createElementAsLastChild(MbJSON.OBJECT,
					"LookupResponse", null);
//			outMessage.getRootElement().createElementAsLastChild(MbJSON.OBJECT,
//					"Error", null);
			
			// Criando o message assembly para retorno
			outAssembly = new MbMessageAssembly(inAssembly, environment,
					inAssembly.getExceptionList(), outMessage);

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
