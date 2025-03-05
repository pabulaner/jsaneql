package de.pabulaner.jsaneql.semana.binding;

import de.pabulaner.jsaneql.algebra.operator.GroupByOperator;

import java.util.List;

public class GroupByScope {

    private final Binding postAggregation;

    private final Binding preAggregation;

    private final List<GroupByOperator.Aggregation> aggregations;

    public GroupByScope(Binding postAggregation, Binding preAggregation, List<GroupByOperator.Aggregation> aggregations) {
        this.postAggregation = postAggregation;
        this.preAggregation = preAggregation;
        this.aggregations = aggregations;

        postAggregation.setGroupByScope(this);
    }

    public Binding getBinding() {
        return postAggregation;
    }

    public Binding getPreAggregation() {
        return preAggregation;
    }

    public List<GroupByOperator.Aggregation> getAggregations() {
        return aggregations;
    }
}
