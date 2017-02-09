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

public class GeraDispVer_JavaCompute_Dat2base64 extends MbJavaComputeNode {

	public void evaluate(MbMessageAssembly inAssembly) throws MbException {
		MbOutputTerminal out = getOutputTerminal("out");
		MbOutputTerminal alt = getOutputTerminal("alternate");

		MbMessage inMessage = inAssembly.getMessage();

		// create new empty message
		MbMessage outMessage = new MbMessage();
		MbMessageAssembly outAssembly = new MbMessageAssembly(inAssembly,
				outMessage);

		try {
			// optionally copy message headers
			// copyMessageHeaders(inMessage, outMessage);
			// ----------------------------------------------------------
			// Add user code below
			BASE64Encoder encoder = new BASE64Encoder();
			byte[] datBase64Bytes = inMessage.getRootElement().getFirstElementByPath("DFDL/ArqDispVerDat").toBitstream(null, null, null, 0, 1208, 0);
			String datBase64 = encoder.encode(datBase64Bytes).replace("\r\n", "");
			
			MbElement outRoot = outMessage.getRootElement();
			MbElement outParser = outRoot.createElementAsLastChild(MbJSON.PARSER_NAME);
			
			MbElement outJsonData = outParser.createElementAsLastChild(MbElement.TYPE_NAME, MbJSON.DATA_ELEMENT_NAME, null);
			MbElement outJsonTest = outJsonData.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "arquivoDat", "DISPVER.DAT");
			// outJsonData = outParser.createElementAsLastChild(MbElement.TYPE_NAME, MbJSON.DATA_ELEMENT_NAME, null);
			outJsonTest = outJsonData.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "dadosDat", datBase64);

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

	public void copyMessageHeaders(MbMessage inMessage, MbMessage outMessage)
			throws MbException {
		MbElement outRoot = outMessage.getRootElement();

		// iterate though the headers starting with the first child of the root
		// element
		MbElement header = inMessage.getRootElement().getFirstChild();
		while (header != null && header.getNextSibling() != null) // stop before
																	// the last
																	// child
																	// (body)
		{
			// copy the header and add it to the out message
			outRoot.addAsLastChild(header.copy());
			// move along to next header
			header = header.getNextSibling();
		}
	}

}
