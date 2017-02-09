package br.org.ons.platform.bus;

import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;
import com.ibm.broker.plugin.MbUserException;

public class CommandRouter_SetValidEndpoint extends MbJavaComputeNode {

	public void evaluate(MbMessageAssembly inAssembly) throws MbException {
		MbOutputTerminal out = getOutputTerminal("out");

		MbMessage inMessage = inAssembly.getMessage();
		MbMessageAssembly outAssembly = null;
		try {
			// ----------------------------------------------------------
			// Add user code below
			MbMessage outMessage = new MbMessage(inMessage);

			// Limpando a URI do HTTPRequest
			MbMessage environment = new MbMessage(
					inAssembly.getLocalEnvironment());
			MbElement environmentRoot = environment.getRootElement();
			environmentRoot.getFirstElementByPath(
					"/Destination/HTTP/RequestLine/RequestURI").delete();

			// Gerando o sufixo de comando da URL a partir do nome do comando
			MbElement commandName = outMessage.getRootElement()
					.getFirstElementByPath("/JSON/Data/command/name");
			String commandNameValue = commandName.getValueAsString();
			String commandSuffix = commandNameValue.substring(commandNameValue
					.lastIndexOf('.') + 1).replaceAll("([a-z])([A-Z]+)", "$1-$2")
                    .toLowerCase();
			
			// Obtendo URL do endpoint e setando na URL do HTTPRequest (com o sufixo no final)
			String endpoint = outMessage.getRootElement().getFirstElementByPath(
							"/LookupResponse/Data/Item/Item/value").getValueAsString();
			environmentRoot
					.evaluateXPath("?Destination/?HTTP/?RequestURL[set-value('"
							+ endpoint + "/" + commandSuffix+ "')]");
			
			// Obtendo o token JWT a partir do header RFH2 e setando no header HTTP
			String token = outMessage.getRootElement().getFirstElementByPath(
					"/MQRFH2/usr/Authorization").getFirstChild().getValueAsString();
			
			// Cria header HTTPRequest depois de Properties
			outMessage.getRootElement().getFirstElementByPath("Properties").createElementAfter(MbElement.TYPE_NAME,"HTTPRequestHeader", null);
			outMessage.getRootElement().getFirstElementByPath("HTTPResponseHeader").delete();
			outMessage.getRootElement().getFirstElementByPath("MQMD").delete();
			outMessage.getRootElement().getFirstElementByPath("MQRFH2").delete();
			
			outMessage.getRootElement().evaluateXPath(
					"?HTTPRequestHeader/?Authorization[set-value('" +
							token + "')]");
			
			// Criando o message assembly para retorno
			outAssembly = new MbMessageAssembly(inAssembly, environment,
					inAssembly.getExceptionList(), outMessage);

			// Limpando os objetos de Lookup mensagem
			outAssembly.getMessage().getRootElement().getFirstElementByPath(
					"/LookupRequest").delete();
			outAssembly.getMessage().getRootElement().getFirstElementByPath(
					"/LookupResponse").delete();
			
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
			// Example handling ensures all exceptions are re-thrown to be handled in the flow
			throw new MbUserException(this, "evaluate()", "", "", e.toString(),
					null);
		}
		// The following should only be changed
		// if not propagating message to the 'out' terminal
		out.propagate(outAssembly);

	}
}
