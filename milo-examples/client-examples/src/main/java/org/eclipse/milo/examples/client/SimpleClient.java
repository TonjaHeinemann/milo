package org.eclipse.milo.examples.client;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.nodes.UaVariableNode;
import org.eclipse.milo.opcua.stack.core.Identifiers;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.examples.server.ExampleServer;

public class SimpleClient {

    private ExampleServer exampleServer;
    private String eUrl;

    public static void main(String[] args) {
        

        SimpleClient simple = new SimpleClient();
        
        simple.eUrl = "opc.tcp://localhost"; // if server is running locally
        try {
            simple.start_server();
            simple.eUrl = "opc.tcp://localhost:12686/milo";
        } catch (Exception e) {
            System.out.println("Exception" + e.getMessage());
        }
        
        simple.do_something();
    }

    public void start_server() throws Exception {
        this.exampleServer = new ExampleServer();
        this.exampleServer.startup().get();
    }

    public void do_something(){
        OpcUaClient client = null;
        try {
            client = OpcUaClient.create(this.eUrl);
            client.connect().get();

            UaVariableNode node = client.getAddressSpace().getVariableNode(Identifiers.Server_ServerStatus_StartTime);
            DataValue value = node.readValue();
            System.out.println("StartTime= " + value.getValue().getValue());

        } catch (Exception e) {
            System.out.println("Exception" + e.getMessage());
        }
        
    }
}
