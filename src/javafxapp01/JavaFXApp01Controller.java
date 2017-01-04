/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapp01;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 *
 * @author ddarling
 */
 
import java.io.IOException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v22.datatype.PN;
import ca.uhn.hl7v2.model.v22.message.ADT_A01;
import ca.uhn.hl7v2.model.v22.segment.MSH;
import ca.uhn.hl7v2.parser.DefaultXMLParser;
import ca.uhn.hl7v2.parser.EncodingNotSupportedException;
import ca.uhn.hl7v2.parser.Parser;
import ca.uhn.hl7v2.parser.XMLParser;
import java.io.StringReader;
import java.io.StringWriter;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.Result;
import javax.xml.transform.Source;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.util.List;
import javax.script.Invocable;

import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;


public class JavaFXApp01Controller implements Initializable {
    
    @FXML
    private MenuBar menubar;
    @FXML
    private TextArea textareaRAW;
    @FXML
    private TextArea textareaXML;
    @FXML
    private TextArea textareaJS;
    @FXML
    private TextArea textareaXMLJ;
    @FXML
    private TextArea textareaXSLT;
    @FXML
    private TextArea textareaXMLT;
    @FXML
    private TextArea textareaJSON;

    String msg = "MSH|^~\\&|HIS|RIH|EKG|EKG|199904140038||ADT^A01||P|2.2\r"
                 + "PID|0001|00009874|00001122|A00977|SMITH^JOHN^M|MOM|19581119|F|NOTREAL^LINDA^M|C|564 SPRING ST^^NEEDHAM^MA^02494^US|0002|(818)565-1551|(425)828-3344|E|S|C|0000444444|252-00-4414||||SA|||SA||||NONE|V1|0001|I|D.ER^50A^M110^01|ER|P00055|11B^M011^02|070615^BATMAN^GEORGE^L|555888^NOTREAL^BOB^K^DR^MD|777889^NOTREAL^SAM^T^DR^MD^PHD|ER|D.WT^1A^M010^01|||ER|AMB|02|070615^NOTREAL^BILL^L|ER|000001916994|D||||||||||||||||GDD|WA|NORM|02|O|02|E.IN^02D^M090^01|E.IN^01D^M080^01|199904072124|199904101200|199904101200||||5555112333|||666097^NOTREAL^MANNY^P\r"
                 + "NK1|0222555|NOTREAL^JAMES^R|FA|STREET^OTHER STREET^CITY^ST^55566|(222)111-3333|(888)999-0000|||||||ORGANIZATION\r"
                 + "PV1|0001|I|D.ER^1F^M950^01|ER|P000998|11B^M011^02|070615^BATMAN^GEORGE^L|555888^OKNEL^BOB^K^DR^MD|777889^NOTREAL^SAM^T^DR^MD^PHD|ER|D.WT^1A^M010^01|||ER|AMB|02|070615^VOICE^BILL^L|ER|000001916994|D||||||||||||||||GDD|WA|NORM|02|O|02|E.IN^02D^M090^01|E.IN^01D^M080^01|199904072124|199904101200|||||5555112333|||666097^DNOTREAL^MANNY^P\r"
                 + "PV2|||0112^TESTING|55555^PATIENT IS NORMAL|NONE|||19990225|19990226|1|1|TESTING|555888^NOTREAL^BOB^K^DR^MD||||||||||PROD^003^099|02|ER||NONE|19990225|19990223|19990316|NONE\r"
                 + "AL1||SEV|001^POLLEN\r"
                 + "GT1||0222PL|NOTREAL^BOB^B||STREET^OTHER STREET^CITY^ST^77787|(444)999-3333|(222)777-5555||||MO|111-33-5555||||NOTREAL GILL N|STREET^OTHER STREET^CITY^ST^99999|(111)222-3333\r"
                 + "IN1||022254P|4558PD|BLUE CROSS|STREET^OTHER STREET^CITY^ST^00990||(333)333-6666||221K|LENIX|||19980515|19990515|||PATIENT01 TEST D||||||||||||||||||02LL|022LP554";

    String xmlRAW = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r" +
                    "<OMDefault xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\r" +
                    "   <PrintDollarsAndCents>X</PrintDollarsAndCents>\r" +
                    "   <MailAddrLine1>Add1</MailAddrLine1>\r" +
                    "   <MailAddrLine2>Add2</MailAddrLine2>\r" +
                    "</OMDefault>";
    
    String xsltMatchReplace = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r" +
                              "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\r" +
                              "    <xsl:template match=\"node()|@*\">\r" +
                              "        <xsl:copy>\r" +
                              "            <xsl:apply-templates select=\"node()|@*\"/>\r" +
                              "        </xsl:copy>\r" +
                              "    </xsl:template>\r" +
                              "    <xsl:template match=\"PrintDollarsAndCents/text()[.='X']\">Y</xsl:template>\r" +
                              "</xsl:stylesheet>";
    
    String jsFunc1 = "var func1 = function(name) {\r" +
                     "    print('Hi there from Javascript, ' + name);\r" +
                     "    return 'greetings by ' + name + ' via javascript function!';\r" +
                     "};";
    
    String jsFunc2 = "var func2 = function (object) {\r" +
                     "    print('JS Class Definition: ' + Object.prototype.toString.call(object));\r" +
                     "};";
    
    @FXML
    private void handleRAWToXML(ActionEvent event) throws HL7Exception {
        
        textareaRAW.setText(msg.replace('\r', '\n'));
 
         /*
          * The HapiContext holds all configuration and provides factory methods for obtaining
          * all sorts of HAPI objects, e.g. parsers. 
          */
         HapiContext context = new DefaultHapiContext();
          
         /*
          * A Parser is used to convert between string representations of messages and instances of
          * HAPI's "Message" object. In this case, we are using a "GenericParser", which is able to
          * handle both XML and ER7 (pipe & hat) encodings.
          */
         Parser p = context.getGenericParser();
         
         try {
        	 context.close();
         } catch (IOException|NullPointerException e) {
        	/* Presently, do nothing as closing
        	 * the context appears to trigger a
        	 * NullPointerException for a currently
        	 * difficult-to-discern reason.
        	 */ 
         }
	  
         Message hapiMsg;
         try {
             // The parse method
        	 // performs the actual parsing
             hapiMsg = p.parse(msg);
         } catch (EncodingNotSupportedException e) {
             e.printStackTrace();
             return;
         } catch (HL7Exception e) {
             e.printStackTrace();
             return;
         }
  
         /*
          * This message, an ADT^A01, is an HL7 data type consisting of several components, so we
          * will cast it as such. The ADT_A01 class extends from Message, providing specialized
          * accessors for ADT^A01's segments.
          * 
          * HAPI provides several versions of the ADT_A01 class, each in a different package (note
          * the import statement above) corresponding to the HL7 version for the message.
          */
         ADT_A01 adtMsg = (ADT_A01)hapiMsg;
 
         MSH msh = adtMsg.getMSH();
 
         // Retrieve some data from the MSH segment
         String msgType = msh.getMessageType().getMessageType().getValue();
         String msgTrigger = msh.getMessageType().getTriggerEvent().getValue();
 
         // Prints "ADT A01"
         System.out.println(msgType + " " + msgTrigger);
 
         /* 
          * Now let's retrieve the patient's name from the parsed message. 
          * 
          * PN is an HL7 data type consisting of several components, such as 
          * family name, given name, etc. 
          */
         PN patientName = adtMsg.getPID().getPatientName();
 
         // Prints "SMITH"
         String familyName = patientName.getFamilyName().getValue();
         System.out.println(familyName);
         
         // encode message in XML
         // and then output it to
         // the current standard out
         XMLParser xmlParser = new DefaultXMLParser();
         String msgInXML = xmlParser.encode(hapiMsg);
         System.out.println(msgInXML);
         
         textareaXML.setText(msgInXML);
    }
    
    @FXML
    private void handleXMLviaJS(ActionEvent event) throws HL7Exception {

        Object result = "";
        
        String jsScript = jsFunc1.replace('\r', '\n');
        
        textareaJS.setText(jsScript);

        try {
            
            ScriptEngineManager factory = new ScriptEngineManager();
            // ScriptEngine scriptEngine = factory.getEngineByName("nashorn");
            ScriptEngine scriptEngine = factory.getEngineByName("javascript");
            scriptEngine.eval(jsScript);
            
            Invocable invocable = (Invocable)scriptEngine;
            result = invocable.invokeFunction("func1", "David Darling");
        } catch (ScriptException | NoSuchMethodException ex) {
            Logger.getLogger(JavaFXApp01Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        textareaXMLJ.setText(result.toString());
    }
    
    @FXML
    private void handleXMLviaXSLT(ActionEvent event) throws HL7Exception {
        
        textareaXSLT.setText("");
        if (xsltMatchReplace != null) {
            textareaXSLT.setText(xsltMatchReplace.replace('\r', '\n'));
        }

        String result = "";
        
        try {
            result = transformXMLviaXSL(xmlRAW.replace('\r', '\n'), xsltMatchReplace.replace('\r', '\n'));
        } catch (TransformerException ex) {
            Logger.getLogger(JavaFXApp01Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        textareaXMLT.setText(result);
    }
    
    @FXML
    private void handleJSONviaXML(ActionEvent event) throws HL7Exception, IOException {
        
        // replace below with HAPIFHIR logic from JvHapiFhir Eclipse project!
        
        XmlMapper xmlMapper = new XmlMapper();
        Object entries = xmlMapper.readValue(xmlRAW, Object.class);
		
        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonEncoded = jsonMapper.writeValueAsString(entries); 
        JsonNode jsonNode = jsonMapper.readValue(jsonEncoded, JsonNode.class);
        String result = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
         
        textareaJSON.setText(result);
    }
    
    @FXML
    private void handleFileClose(ActionEvent event) {
        /*
        Call getScene() on any scene graph node
        which you know to be in the same scene
        as the menubar containing your menu item
        */
        ((Stage)menubar.getScene().getWindow()).close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    private String transformXMLviaXSL(String inputXML, String inputXSL)
            throws TransformerConfigurationException,
            TransformerException
    {
        
        Source xmlInput = new StreamSource(new StringReader(inputXML));
        Source xslInput = new StreamSource(new StringReader(inputXSL));

        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer transformer = tFactory.newTransformer(xslInput); // this is the line that throws the exception

        StringWriter stringWriter = new StringWriter();
        StreamResult result = new StreamResult(stringWriter);
        transformer.transform(xmlInput, result);

        return stringWriter.toString();
    }

    private String transformXMLviaJS(String inputXML, String inputJS)
            throws TransformerConfigurationException,
            TransformerException
    {
        
        Source xmlInput = new StreamSource(new StringReader(inputXML));
        Source jsInput = new StreamSource(new StringReader(inputJS));

        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer transformer = tFactory.newTransformer(jsInput); // this is the line that throws the exception

        StringWriter stringWriter = new StringWriter();
        StreamResult result = new StreamResult(stringWriter);
        transformer.transform(xmlInput, result);

        return stringWriter.toString();
    }
    
}
