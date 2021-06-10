package fr.epita.assistants.myide.ricains.service;

import java.util.ArrayList;
import java.util.List;

import fr.epita.assistants.myide.domain.entity.Node;
import fr.epita.assistants.myide.domain.entity.Node.Type;
import fr.epita.assistants.myide.domain.service.NodeService;

public class RicainsNodeService implements NodeService {

    private final List<Node> nodesList;

    public RicainsNodeService() {
        this.nodesList = new ArrayList<>();
    }

    @Override
    public Node update(Node node, int from, int to, byte[] insertedContent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean delete(Node node) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Node create(Node folder, String name, Type type) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Node move(Node nodeToMove, Node destinationFolder) {
        // TODO Auto-generated method stub
        return null;
    }

}
