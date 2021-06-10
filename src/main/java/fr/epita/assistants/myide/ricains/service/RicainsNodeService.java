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
        String mystring = "";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                mystring += line + "\n";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String mystring2 = mystring.substring(0, from);
        mystring2 += insertedContent;
        mystring2 += mystring.substring(to + 1);
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(mystring2);
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
            file.delete();
            return true;
        }
        return false;
    }

    @Override
    public Node create(Node folder, String name, Type type) {
        Node mynode = new RicainsNode(folder, name, type);
        return mynode;
    }

    @Override
    public Node move(Node nodeToMove, Node destinationFolder) {
        try {
            Files.move(nodeToMove.getPath().toFile().toPath(), destinationFolder.getPath().toFile().toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        RicainsNode nodemoved = new RicainsNode(destinationFolder, destinationFolder.getPath().toString() + nodeToMove.getPath().toFile().getName(), nodeToMove.getType());
        delete(nodeToMove);
        return nodemoved;
    }

}
