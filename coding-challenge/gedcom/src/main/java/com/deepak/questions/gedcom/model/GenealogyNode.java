package com.deepak.questions.gedcom.model;

public class GenealogyNode {
    private String nodeName;
    private String nodeValue;

    public GenealogyNode(String nodeName, String nodeValue) {
        super();
        this.nodeName = nodeName;
        this.nodeValue = nodeValue;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nodeName == null) ? 0 : nodeName.hashCode());
        result = prime * result + ((nodeValue == null) ? 0 : nodeValue.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        GenealogyNode other = (GenealogyNode) obj;
        if (nodeName == null) {
            if (other.nodeName != null) return false;
        } else if (!nodeName.equals(other.nodeName)) return false;
        if (nodeValue == null) {
            if (other.nodeValue != null) return false;
        } else if (!nodeValue.equals(other.nodeValue)) return false;
        return true;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeValue() {
        return nodeValue;
    }

    public void setNodeValue(String nodeValue) {
        this.nodeValue = nodeValue;
    }

    @Override
    public String toString() {
        return "GenealogyNode [nodeName=" + nodeName + ", nodeValue=" + nodeValue + "]";
    }

}
