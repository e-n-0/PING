package fr.epita.assistants.myide.ricains.service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import fr.epita.assistants.myide.domain.entity.Node;
import fr.epita.assistants.myide.domain.entity.Node.Type;
import fr.epita.assistants.myide.domain.entity.Node.Types;
import fr.epita.assistants.myide.domain.service.NodeService;
import fr.epita.assistants.myide.ricains.entity.RicainsNode;
import fr.epita.assistants.utils.Log;

public class RicainsNodeService implements NodeService {
    private final List<Node> nodes = new ArrayList<>();

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
                myString = myString + line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String myString2 = "";
        String s = new String(insertedContent, StandardCharsets.UTF_8);
        if (from < myString.length()) {
            myString2 = myString.substring(0, from);
            myString2 = myString2 + s;
            if (myString.length() > to) {
                myString2 = myString2 + myString.substring(to);
            }
        } else {
            myString2 = myString + s;
        }

        try {
            FileWriter fw = new FileWriter(file);
            fw.write(myString2);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return node;
    }

    static boolean deleteDir(File file) {
        String[]entries = file.list();
        if (file.isDirectory()) {
            for (String s : entries) {
                File currentFile = new File(file.getPath(), s);
                deleteDir(currentFile);
            }
        }
        return file.delete();
    }

    @Override
    public boolean delete(Node node) {
        File file = node.getPath().toFile();
        if (file.exists()) {
            return deleteDir(file);
        }
        return false;
    }

    @Override
    public Node create(Node folder, String name, Type type) {
        Node mynode;
        String path = Path.of(folder.getPath().toString(), name).toString();
        File myFile = new File(path);

        if (type.equals(Types.FOLDER))
            myFile.mkdirs();
        else {
            try {
                if (!myFile.createNewFile())
                    throw new Exception("Failed to create a file");
            } catch (Exception ioException) {
                ioException.printStackTrace();
                throw new RuntimeException("hard fail");
            }
        }

        mynode = new RicainsNode(path, type);
        nodes.add(mynode);
        return mynode;
    }

    @Override
    public Node move(Node nodeToMove, Node destinationFolder) {

        if (nodeToMove == null || destinationFolder == null)
            throw new RuntimeException("Parameters are null");

        if (destinationFolder.isFile()) {
            try {
                throw new Exception("Destination is not a directory");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        var newPath = Path.of(destinationFolder.getPath().toString(), nodeToMove.getPath().getFileName().toString());
        Log.log(newPath);

        try {
            Files.move(nodeToMove.getPath(), newPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            return nodeToMove;
        }

        RicainsNode nodeMoved = new RicainsNode(newPath.toString(), nodeToMove.getType());
        delete(nodeToMove);
        return nodeMoved;
    }

}
