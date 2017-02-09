package br.org.ons.platform.bus;

import com.ibm.broker.config.proxy.BrokerProxy;
import com.ibm.broker.config.proxy.ConfigurableService;
import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.javastartparameters.JavaStartParameters;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;
import com.ibm.broker.plugin.MbUserException;

public class HttpRouter_GetServiceName extends MbJavaComputeNode {

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
