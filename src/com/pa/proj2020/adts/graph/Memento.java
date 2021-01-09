package com.pa.proj2020.adts.graph;

import java.util.List;

public interface Memento {
    <T> List<T> getState();
}