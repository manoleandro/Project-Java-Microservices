package br.org.ons.platform.bus;

import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;
import com.ibm.broker.plugin.MbUserException;

public class CommandProcessor_CheckReplyToQ extends MbJavaComputeNode {

	public void evaluate(MbMessageAssembly inAssembly) throws MbException {
		MbOutputTerminal out = getOutputTerminal("out");

		MbMessage inMessage = inAssembly.getMessage();
		MbMessageAssembly outAssembly = null;
		try {
			// ----------------------------------------------------------
			// Add user code below

			MbMessage outMessage = new MbMessage(inMessage);
			MbMessage environment = new MbMessage(
					inAssembly.getLocalEnvironment());
			
			// Verificando se o cabeçalho ReplyToQ foi definido pelo cliente
			MbElement replyToQ = inMessage.getRootElement()
					.getFirstElementByPath("/MQMD/ReplyToQ");
			if (replyToQ.getValueAsString() != null
					&& replyToQ.getValueAsString().trim().length() > 0) {
				
				// Limpando os Headers HTTP da mensagem
				outMessage.getRootElement()
						.getFirstElementByPath("/HTTPRequestHeader").delete();
				outMessage.getRootElement()
						.getFirstElementByPath("/HTTPResponseHeader").delete();
				
				// Setando o Type e Expiry
				outMessage.getRootElement().evaluateXPath(
						"MQRFH2/mcd/Type[set-value('br.org.ons.platform.common.ResultMessage')]");				
				outMessage.getRootElement().getFirstElementByPath(
						"MQMD/Expiry").setValue(1200L);

				// Criando o message assembly para retorno
				outAssembly = new MbMessageAssembly(inAssembly, environment,
						inAssembly.getExceptionList(), outMessage);
				
				// Propagando somente se o cliente estiver esperando resposta
				out.propagate(outAssembly);
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
			// Example handling ensures all exceptions are re-thrown to be handled in the flow
			throw new MbUserException(this, "evaluate()", "", "", e.toString(),
					null);
		}
	}
}
