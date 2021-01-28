package smartgraph.view.graphview;

import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.function.Consumer;

public class SmartGraphPanelClick<V, E> extends Pane { /*
    INTERACTION WITH VERTICES AND EDGES
     */
    private Consumer<SmartGraphVertex<V>> vertexClickConsumer = null;
    private Consumer<SmartGraphEdge<E, V>> edgeClickConsumer = null;

    public void setVertexClickConsumer(Consumer<SmartGraphVertex<V>> vertexClickConsumer) {
        this.vertexClickConsumer = vertexClickConsumer;
    }

    public void setEdgeClickConsumer(Consumer<SmartGraphEdge<E, V>> edgeClickConsumer) {
        this.edgeClickConsumer = edgeClickConsumer;
    }

    /**
     * Enables the double click action on this pane.
     * <p>
     * This method identifies the node that was clicked and, if any, calls the
     * appropriate consumer, i.e., vertex or edge consumers.
     */
    public void enableDoubleClickListener(SmartGraphPanel smartGraphPanel) {
        smartGraphPanel.setOnMouseClicked((MouseEvent mouseEvent) -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                if (mouseEvent.getClickCount() == 2) {
                    //no need to continue otherwise
                    if (vertexClickConsumer == null && edgeClickConsumer == null) {
                        return;
                    }

                    Node node = UtilitiesJavaFX.pick(smartGraphPanel, mouseEvent.getSceneX(), mouseEvent.getSceneY());
                    if (node == null) {
                        return;
                    }

                    if (node instanceof SmartGraphVertex) {
                        SmartGraphVertex v = (SmartGraphVertex) node;
                        vertexClickConsumer.accept(v);
                    } else if (node instanceof SmartGraphEdge) {
                        SmartGraphEdge e = (SmartGraphEdge) node;
                        edgeClickConsumer.accept(e);
                    }

                }
            }
        });
    }
}