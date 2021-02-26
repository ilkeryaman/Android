package com.reset4.fourwork.engine.general;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Created by eilkyam on 13.06.2016.
 */
public class XmlOperations {
    /**
     * Returns the value of the child node with the given name.
     * @param base the element from where to search.
     * @param name of the element to get.
     * @return the value of this element.
     */
    public static Node getChildNodeOfTypeElement(final Node base, final String name) {
        Node childNode = null;
        NodeList nodeList = base.getChildNodes();
        if (nodeList.getLength() > 0) {
            int length = nodeList.getLength();
            for (int i = 0; i < length; i++) {
                Node node = nodeList.item(i);

                // Get an element, create an instance of the element
                if (Node.ELEMENT_NODE == node.getNodeType()) {
                    if (name.equals(node.getNodeName())) {
                        childNode = node;
                    }
                }
            }
        }
        return childNode;
    }
}
