package org.eclipse.milo.examples.client;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.nodes.UaVariableNode;
import org.eclipse.milo.opcua.stack.core.Identifiers;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;

public class SimpleClient {
    public static void main(String[] args) {
        SimpleClient simple = new SimpleClient();

        simple.do_something();
    }

    public void do_something(){
        OpcUaClient client = null;
        try {
            client = OpcUaClient.create("opc.tcp://localhost");
            client.connect().get();

            UaVariableNode node = client.getAddressSpace().getVariableNode(Identifiers.Server_ServerStatus_StartTime);
            DataValue value = node.readValue();
            System.out.println("StartTime= " + value.getValue().getValue());

        } catch (Exception e) {
            System.out.println("Exception" + e.getMessage());
        }
        
    }
}
