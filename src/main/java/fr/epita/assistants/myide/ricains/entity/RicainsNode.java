package fr.epita.assistants.myide.ricains.entity;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import fr.epita.assistants.myide.domain.entity.Node;

public class RicainsNode implements Node {

    private Path path;
    private final Type type;
    private Node parent;

    public RicainsNode(Node Folder, String name, Type type) {
        this.parent = Folder;
        this.path = Paths.get(name);
        this.type = type;
    }

    @Override
    public @NotNull Path getPath() {
        return this.path;
    }

    @Override
    public @NotNull Type getType() {
        return this.type;
    }

    @Override
    public @NotNull List<@NotNull Node> getChildren() {
        List<Node> childrens = new ArrayList<>();
        if (type != Types.FILE) {
            File folder = path.toFile();
            File[] listfile = folder.listFiles();

            for (int i = 0; i < listfile.length; i++) {
                File selected = listfile[i];
                String filename = Paths.get(selected.getPath()).toString();
                Type selectedType = selected.isFile() ? Types.FILE : Types.FOLDER;
                RicainsNode node = new RicainsNode(this, filename, selectedType);
                childrens.add(node);
            }
        }
        return childrens;
    }

}
