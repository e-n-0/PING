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

    public RicainsNode(String path, Type type) {
        this.path = Paths.get(path);
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
        List<Node> children = new ArrayList<>();
        if (type != Types.FILE) {
            File folder = path.toFile();
            File[] listFile = folder.listFiles();

            for (int i = 0; i < listFile.length; i++) {
                File selected = listFile[i];
                String filename = Paths.get(selected.getPath()).toString();
                Type selectedType = selected.isFile() ? Types.FILE : Types.FOLDER;
                RicainsNode node = new RicainsNode(filename, selectedType);
                children.add(node);
            }
        }
        return children;
    }

}
