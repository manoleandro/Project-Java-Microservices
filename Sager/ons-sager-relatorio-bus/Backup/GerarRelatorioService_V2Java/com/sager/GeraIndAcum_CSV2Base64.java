package com.sager;

import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbJSON;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;
import com.ibm.broker.plugin.MbUserException;
import com.ibm.misc.BASE64Encoder;

public class GeraIndAcum_CSV2Base64 extends MbJavaComputeNode {

	public void evaluate(MbMessageAssembly assembly) throws MbException {
		MbOutputTerminal out = getOutputTerminal("out");
		MbOutputTerminal alt = getOutputTerminal("alternate");

		try {
			MbMessage message = assembly.getMessage();
			// ----------------------------------------------------------
			// Add user code below
			BASE64Encoder encoder = new BASE64Encoder();
			byte[] datBase64Bytes = message.getRootElement().getFirstElementByPath("DFDL/ArqIndAcumDat").toBitstream(null, null, null, 0, 1208, 0);
			String datBase64 = encoder.encode(datBase64Bytes).replace("\r\n", "");
					
			MbElement env = assembly.getLocalEnvironment().getRootElement();
			// env.createElementAsFirstChild(MbJSON.PARSER_NAME);
			MbElement outEnvJsonData = env.createElementAsLastChild(MbElement.TYPE_NAME, MbJSON.DATA_ELEMENT_NAME, null);
			MbElement outEnvJsonElement = outEnvJsonData.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "dadosDat", datBase64);

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

		// Change following to propagate the message to the 'out' or 'alt' terminal
		out.propagate(assembly);
	}

}
