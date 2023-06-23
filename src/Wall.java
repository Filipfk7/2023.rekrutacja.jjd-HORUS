import java.util.*;

interface Structure {
    Optional<Block> findBlockByColor(String color);
    List<Block> findBlocksByMaterial(String material);
    int count();
}

public class Wall implements Structure {
    private List<Block> blocks;

    public Wall(List<Block> blocks) {
        this.blocks = blocks;
    }

    @Override
    public Optional<Block> findBlockByColor(String color) {
        Queue<Block> queue = new LinkedList<>(blocks);

        while (!queue.isEmpty()) {
            Block block = queue.poll();
            if (block.getColor().equals(color)) {
                return Optional.of(block);
            }

            addCompositeBlocks(queue, block);
        }

        return Optional.empty();
    }

    @Override
    public List<Block> findBlocksByMaterial(String material) {
        List<Block> matchingBlocks = new ArrayList<>();
        Queue<Block> queue = new LinkedList<>(blocks);

        while (!queue.isEmpty()) {
            Block block = queue.poll();
            if (block.getMaterial().equals(material)) {
                matchingBlocks.add(block);
            }

            addCompositeBlocks(queue, block);
        }

        return matchingBlocks;
    }

    @Override
    public int count() {
        int total = 0;
        Queue<Block> queue = new LinkedList<>(blocks);

        while (!queue.isEmpty()) {
            Block block = queue.poll();
            total++;

            addCompositeBlocks(queue, block);
        }

        return total;
    }

    private static void addCompositeBlocks(Queue<Block> queue, Block block) {
        if (block instanceof CompositeBlock) {
            CompositeBlock compositeBlock = (CompositeBlock) block;
            queue.addAll(compositeBlock.getBlocks());
        }
    }
}

interface Block {
    String getColor();
    String getMaterial();
}

interface CompositeBlock extends Block {
    List<Block> getBlocks();
}
