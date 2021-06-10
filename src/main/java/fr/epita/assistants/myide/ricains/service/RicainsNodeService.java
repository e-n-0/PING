package fr.epita.assistants.myide.ricains.service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import fr.epita.assistants.myide.domain.entity.Node;
import fr.epita.assistants.myide.domain.entity.Node.Type;
import fr.epita.assistants.myide.domain.service.NodeService;
import fr.epita.assistants.myide.ricains.entity.RicainsNode;

public class RicainsNodeService implements NodeService {

    public RicainsNodeService() {

    }

    @Override
    public Node update(Node node, int from, int to, byte[] insertedContent) {
        if (node.isFolder()) {
            try {
                throw new Exception("Not a file!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        File file = node.getPath().toFile();
        String myString = "";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                myString = myString + (line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String myString2 = myString.substring(0, from);
        myString2 = myString2 + insertedContent;
        myString2 = myString2 + myString.substring(to + 1);
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(myString2);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return node;
    }

    @Override
    public boolean delete(Node node) {
        File file = node.getPath().toFile();
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }

    @Override
    public Node create(Node folder, String name, Type type) {
        String path = folder.getPath().getFileName().toString() + name;
        return new RicainsNode(path, type);
    }

    @Override
    public Node move(Node nodeToMove, Node destinationFolder) {
        try {
            Files.move(nodeToMove.getPath().toFile().toPath(), destinationFolder.getPath().toFile().toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        RicainsNode nodeMoved = new RicainsNode(destinationFolder.getPath().toString() + nodeToMove.getPath().toFile().getName(), nodeToMove.getType());
        delete(nodeToMove);
        return nodeMoved;
    }

}
