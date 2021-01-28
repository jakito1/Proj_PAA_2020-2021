package smartgraph.view.graphview;

import com.pa.proj2020.adts.graph.Vertex;
import javafx.geometry.Point2D;

import java.util.Map;

public class SmartGraphPanelForces<V> {
    private final double repulsionForce;
    private final double attractionForce;
    private final double attractionScale;

    public SmartGraphPanelForces(SmartGraphProperties smartGraphProperties) {
        this.repulsionForce = smartGraphProperties.getRepulsionForce();
        this.attractionForce = smartGraphProperties.getAttractionForce();
        this.attractionScale = smartGraphProperties.getAttractionScale();
    }/*
     * AUTOMATIC LAYOUT
     */

    public void computeForces(Map<Vertex<V>, SmartGraphVertexNode<V>> thisVertexNodes, SmartGraphPanel smartGraphPanel) {
        for (SmartGraphVertexNode<V> v : thisVertexNodes.values()) {
            for (SmartGraphVertexNode<V> other : thisVertexNodes.values()) {
                if (v == other) {
                    continue; //NOP
                }

                //double k = Math.sqrt(getWidth() * getHeight() / graphVertexMap.size());
                Point2D repellingForce = UtilitiesPoint2D.repellingForce(v.getUpdatedPosition(), other.getUpdatedPosition(), this.repulsionForce);

                double deltaForceX, deltaForceY;

                //compute attractive and repelling forces
                //opt to use internal areAdjacent check, because a vertex can be removed from
                //the underlying graph before we have the chance to remove it from our
                //internal data structure
                if (smartGraphPanel.areAdjacent(v, other)) {

                    Point2D attractiveForce = UtilitiesPoint2D.attractiveForce(v.getUpdatedPosition(), other.getUpdatedPosition(),
                            thisVertexNodes.size(), this.attractionForce, this.attractionScale);

                    deltaForceX = attractiveForce.getX() + repellingForce.getX();
                    deltaForceY = attractiveForce.getY() + repellingForce.getY();
                } else {
                    deltaForceX = repellingForce.getX();
                    deltaForceY = repellingForce.getY();
                }

                v.addForceVector(deltaForceX, deltaForceY);
            }
        }
    }
}