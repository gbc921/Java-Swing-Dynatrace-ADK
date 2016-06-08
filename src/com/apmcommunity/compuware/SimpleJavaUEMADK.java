package com.apmcommunity.compuware;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class SimpleJavaUEMADK {
/*  Example:
	GET http://…/dynaTraceMonitor?
	info=
	{o:c|b:easyTravel mobile|c:127116353789585|d:4|f:1|g :WiFi|h:easyTravel|i:320|j:480|n:802.11x|u:Mac OS X 10.8|v:X86_ARCH1|w:iOS Simulator|x: Apple}
	{o:b|c:en_US|d:120|n:4.2.0.3133|u:1.0|v:127116353789585}
	{o:a|a:performLogin|b:186 50|c:18681|d:1|e:5|t:1348216920488|y:1|z:0}
	{o:a|a:LoginSuccessful|b:18675|c:18675|d:1| e:4|t:1348216920488|z:1}
	{o:a|a:labuser|b:18678|c:18678|d:1|e:4|t:1348216920488|z:1}
	{o:v|b:1348216897375|f:23112|h:141|k:8192|l:84} 
*/
	
	public static void sendPageActionToDynaTraceUEM(int visitID, String applicationName, String pageActionName, long pageActionExecTime) {
			// <ADK>
			SimpleJavaUEMADK.CommonSegment commonSegment = new SimpleJavaUEMADK.CommonSegment(
					null, 										// a_AccountID
					applicationName, 							// b_ApplicationID		*required
					Integer.toString(visitID), 					// c_VisitorID			*required
					"1", 										// d_SessionID			*required
					null, 										// e_GroupID
					"1", 										// f_PageSequence
					"WiFi", 									// g_ConnectionType		*required
					applicationName, 							// h_ApplicationName	*required
					"1920", 									// i_ScreenWidth		*required
					"1080", 									// j_ScreenHeight		*required
					null, 										// k_DeviceLocation
					"802.11x", 									// n_NetworkProtocol
					null, 										// p_NetworkSignalStrength
					null, 										// r_SampleRate
					null, 										// s_Wrate
					System.getProperty("os.name"), 				// u_OS					*required
					System.getProperty("os.arch"),				// v_CPUInfo
					"Rotação de Imagens (AWT, Swing)",			// w_DeviceType			*required
					System.getProperty("java.vendor")			// x_Manufacturer		*required
					);
			SimpleJavaUEMADK.BasicSegment basicSegment = new SimpleJavaUEMADK.BasicSegment(
					"en_US", 									// c_UserLanguage
					"120", 										// d_TimeOffsetGMT
					null, 										// g_SupportJava
					null, 										// i_FlashVersion
					null, 										// j_BrowserVersion
					null, 										// k_BrowserType
					"4.2.0.3133", 								// n_AgentApiVersion	*required
					null, 										// p_UserAgentString	*required
					"1.0", 										// u_ApplicationVersion 
					Integer.toString(visitID),					// v_DeviceID			*required
					null, 										// x_DeviceCarrier
					null  										// y_DeviceCapabilities
					);
			
			SimpleJavaUEMADK.CustomSegment customSegment = new SimpleJavaUEMADK.CustomSegment(
					pageActionName, 							// a_Name				*required
					"0",										// b_EventStartTime		*required
					Long.toString(pageActionExecTime),			// c_EventEndTime		*required
					"1348216920488", 							// t_EventSendTime		*required
					"5", 										// e_EventType			*required
					"1", 										// y_CurrentTagID		*required
					"0"  										// z_ParentTagID		*required
					);
			
			SimpleJavaUEMADK.VerboseSegment verboseSegment = new SimpleJavaUEMADK.VerboseSegment(
					"1348216897375",		 					// b_TagStartTime		*required
					null, 										// e_TagStopTime		*required
					"23112", 									// f_ExecutionTime		*required
					null, 										// g_DeviceBatteryStren	*required
					"141", 										// h_RunningProcesses	*required
					null, 										// j_UNKNOWN
					"8192", 									// k_DeviceMemorySize	*required
					"84"  										// l_DeviceMemoryFree	*required
					);
			
			// </ADK>
			SimpleJavaUEMADK.post("localhost", "80", commonSegment, basicSegment, customSegment, verboseSegment);
	}
	
	private static void post(String webServerHostWithUEM, String webServerPortWithUEM, CommonSegment commonSegment, BasicSegment basicSegment, CustomSegment customSegment, VerboseSegment verboseSegment) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("http://").append(webServerHostWithUEM).append(":").append(webServerPortWithUEM);
		stringBuffer.append("/dynaTraceMonitor?info=");
		if (commonSegment != null) 	stringBuffer.append(commonSegment); 
		if (basicSegment != null) 	stringBuffer.append(basicSegment); 
		if (customSegment != null) 	stringBuffer.append(customSegment); 
		if (verboseSegment != null) stringBuffer.append(verboseSegment); 
		
		// HTTP GET
		String addr = stringBuffer.toString().replaceAll(" ", "%20"); // Yeah, it's getting dirty here, but for a proof of concept okay ;-)
		try{
			URL url = new URL(addr);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");

			connection.connect();
			InputStream in = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String text = reader.readLine();
			System.out.println(text);

			connection.disconnect();
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	private static class BasicSegment {
		private StringBuffer stringBuffer = new StringBuffer();
		/**
s		 * @param c_UserLanguage		
		 * @param d_TimeOffsetGMT
		 * @param g_SupportJava
		 * @param i_FlashVersion
		 * @param j_BrowserVersion
		 * @param k_BrowserType
		 * @param n_AgentApiVersion			*required
		 * @param p_UserAgentString			*required
		 * @param u_ApplicationVersion
		 * @param v_DeviceID				*required
		 * @param x_DeviceCarrier
		 * @param y_DeviceCapabilities
		 */
		public BasicSegment(String c_UserLanguage, String d_TimeOffsetGMT, String g_SupportJava, String i_FlashVersion, String j_BrowserVersion, String k_BrowserType, String n_AgentApiVersion, String p_UserAgentString, String u_ApplicationVersion, String v_DeviceID, String x_DeviceCarrier, String y_DeviceCapabilities) {
			stringBuffer.append("{");
			stringBuffer.append("o:b");
			if (c_UserLanguage != null) 			stringBuffer.append("|c:").append(c_UserLanguage);
			if (d_TimeOffsetGMT != null) 			stringBuffer.append("|d:").append(d_TimeOffsetGMT);
			if (g_SupportJava != null) 				stringBuffer.append("|g:").append(g_SupportJava);
			if (i_FlashVersion != null) 			stringBuffer.append("|i:").append(i_FlashVersion);
			if (k_BrowserType != null) 				stringBuffer.append("|k:").append(k_BrowserType);
			if (n_AgentApiVersion != null) 			stringBuffer.append("|n:").append(n_AgentApiVersion);
			if (p_UserAgentString != null)			stringBuffer.append("|p:").append(p_UserAgentString);
			if (u_ApplicationVersion != null)		stringBuffer.append("|u:").append(u_ApplicationVersion);
			if (v_DeviceID != null)					stringBuffer.append("|v:").append(v_DeviceID);
			if (x_DeviceCarrier != null)			stringBuffer.append("|x:").append(x_DeviceCarrier);
			if (y_DeviceCapabilities != null)		stringBuffer.append("|y:").append(y_DeviceCapabilities);
			stringBuffer.append("}");		}
		
		@Override
		public String toString() {
			return stringBuffer.toString();
		}
	}
	
	private static class CommonSegment {
		private StringBuffer stringBuffer = new StringBuffer();
		/**
		 * @param a_AccountID
		 * @param b_ApplicationID			*required
		 * @param c_VisitorID				*required
		 * @param d_SessionID				*required
		 * @param e_GroupID
		 * @param f_PageSequence
		 * @param g_ConnectionType			*required
		 * @param h_ApplicationName			*required
		 * @param i_ScreenWidth				*required
		 * @param j_ScreenHeight			*required
		 * @param k_DeviceLocation
		 * @param n_NetworkProtocol
		 * @param p_NetworkSignalStrenght
		 * @param r_SampleRate
		 * @param s_Wrate
		 * @param u_OS						*required
		 * @param v_CPUInfo
		 * @param w_DeviceType				*required
		 * @param x_Manufacturer			*required
		 */
		public CommonSegment(String a_AccountID, String b_ApplicationID, String c_VisitorID, String d_SessionID, String e_GroupID, String f_PageSequence, String g_ConnectionType, String h_ApplicationName, String i_ScreenWidth, String j_ScreenHeight, String k_DeviceLocation, String n_NetworkProtocol, String p_NetworkSignalStrenght, String r_SampleRate, String s_Wrate, String u_OS, String v_CPUInfo, String w_DeviceType, String x_Manufacturer) {
			stringBuffer.append("{");
			stringBuffer.append("o:c");
			if (a_AccountID != null) 				stringBuffer.append("|a:").append(a_AccountID);
			if (b_ApplicationID != null) 			stringBuffer.append("|b:").append(b_ApplicationID);
			if (c_VisitorID != null) 				stringBuffer.append("|c:").append(c_VisitorID);
			if (d_SessionID != null) 				stringBuffer.append("|d:").append(d_SessionID);
			if (e_GroupID != null) 					stringBuffer.append("|e:").append(e_GroupID);
			if (f_PageSequence != null) 			stringBuffer.append("|f:").append(f_PageSequence);
			if (g_ConnectionType != null)			stringBuffer.append("|g:").append(g_ConnectionType);
			if (h_ApplicationName != null)			stringBuffer.append("|h:").append(h_ApplicationName);
			if (i_ScreenWidth != null)				stringBuffer.append("|i:").append(i_ScreenWidth);
			if (j_ScreenHeight != null)				stringBuffer.append("|j:").append(j_ScreenHeight);
			if (k_DeviceLocation != null)			stringBuffer.append("|k:").append(k_DeviceLocation);
			if (n_NetworkProtocol != null)			stringBuffer.append("|n:").append(n_NetworkProtocol);
			if (p_NetworkSignalStrenght != null)	stringBuffer.append("|p:").append(p_NetworkSignalStrenght);
			if (r_SampleRate != null)				stringBuffer.append("|r:").append(r_SampleRate);
			if (s_Wrate != null)					stringBuffer.append("|s:").append(s_Wrate);
			if (u_OS != null)						stringBuffer.append("|u:").append(u_OS);
			if (v_CPUInfo != null)					stringBuffer.append("|v:").append(v_CPUInfo);
			if (w_DeviceType != null)				stringBuffer.append("|w:").append(w_DeviceType);
			if (x_Manufacturer != null)				stringBuffer.append("|x:").append(x_Manufacturer);
			stringBuffer.append("}");			
		}
		
		@Override
		public String toString() {
			return stringBuffer.toString();
		}
	}

	private static class CustomSegment {
		private StringBuffer stringBuffer = new StringBuffer();
		/**
		 * @param a_Name					*required
		 * @param b_EventStartTime			*required
		 * @param c_EventEndTime			*required
		 * @param t_EventSendTime			*required
		 * @param e_EventType				*required
		 * @param y_CurrentTagID			*required
		 * @param z_ParentTagID				*required
		 */
		public CustomSegment(String a_Name, String b_EventStartTime, String c_EventEndTime, String t_EventSendTime, String e_EventType, String y_CurrentTagID, String z_ParentTagID){
			stringBuffer.append("{");
			stringBuffer.append("o:a");
			if (a_Name != null) 					stringBuffer.append("|a:").append(a_Name);
			if (b_EventStartTime != null) 			stringBuffer.append("|b:").append(b_EventStartTime);
			if (c_EventEndTime != null) 			stringBuffer.append("|c:").append(c_EventEndTime);
			if (t_EventSendTime != null) 			stringBuffer.append("|t:").append(t_EventSendTime);
			if (e_EventType != null) 				stringBuffer.append("|e:").append(e_EventType);
			if (y_CurrentTagID != null) 			stringBuffer.append("|y:").append(y_CurrentTagID);
			if (z_ParentTagID != null)				stringBuffer.append("|z:").append(z_ParentTagID);
			stringBuffer.append("}");
		}
		
		@Override
		public String toString() {
			return stringBuffer.toString();
		}
	}
	
	private static class VerboseSegment {
		private StringBuffer stringBuffer = new StringBuffer();
		/**
		 * @param b_TagStartTime			*required
		 * @param e_TagStopTime				*required
		 * @param f_ExecutionTime			*required
		 * @param g_DeviceBatteryStrenght	*required
		 * @param h_RunningProcesses		*required
		 * @param j_UNKNOWN					*WTF??
		 * @param k_DeviceMemorySize		*required
		 * @param l_DeviceMemoryFree		*required
		 */
		public VerboseSegment(String b_TagStartTime, String e_TagStopTime, String f_ExecutionTime, String g_DeviceBatteryStrenght, String h_RunningProcesses, String j_UNKNOWN, String k_DeviceMemorySize, String l_DeviceMemoryFree) {
			stringBuffer.append("{");
			stringBuffer.append("o:v");
			if (b_TagStartTime != null) 			stringBuffer.append("|b:").append(b_TagStartTime);
			if (e_TagStopTime != null) 				stringBuffer.append("|e:").append(e_TagStopTime);
			if (f_ExecutionTime != null) 			stringBuffer.append("|f:").append(f_ExecutionTime);
			if (g_DeviceBatteryStrenght != null)	stringBuffer.append("|g:").append(g_DeviceBatteryStrenght);
			if (h_RunningProcesses != null) 		stringBuffer.append("|h:").append(h_RunningProcesses);
			if (j_UNKNOWN != null) 					stringBuffer.append("|j:").append(j_UNKNOWN);
			if (k_DeviceMemorySize != null)			stringBuffer.append("|k:").append(k_DeviceMemorySize);
			if (l_DeviceMemoryFree != null)			stringBuffer.append("|l:").append(l_DeviceMemoryFree);
			stringBuffer.append("}");			
		}
		
		@Override
		public String toString() {
			return stringBuffer.toString();
		}
	}
	
}
