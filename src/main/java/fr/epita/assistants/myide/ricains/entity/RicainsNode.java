package fr.epita.assistants.myide.ricains.entity;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import fr.epita.assistants.myide.domain.entity.Node;

public class RicainsNode implements Node {

    private final Path path;
    private final Type type;
    private final List<Node> childrens;

    public RicainsNode(String name, Type type) {
        this.path = Paths.get(name);
        this.type = type;
        this.childrens = new ArrayList<>();

        if (type != Types.FILE) {
            File folder = path.toFile();
            File[] listfile = folder.listFiles();

            for (int i = 0; i < listfile.length; i++) {
                File selected = listfile[i];
                String filename = Paths.get(selected.getPath()).toString();
                Type selectedType = selected.isFile() ? Types.FILE : Types.FOLDER;
                RicainsNode node = new RicainsNode(filename, selectedType);
                this.childrens.add(node);
            }
        }
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
        return this.childrens;
    }

}
