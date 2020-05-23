package br.edu.ifpb.mestrado.openplanner.api.presentation.dto.shared;

import java.io.Serializable;

public class EnumOptionTO<T extends Enum<?>> implements Serializable {

    private static final long serialVersionUID = -8291862912024594073L;

    private T value;

    private String label;

    public EnumOptionTO() {
        super();
    }

    public EnumOptionTO(T value, String label) {
        super();
        this.value = value;
        this.label = label;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
