package ru.netology.graphics.image;

public class TextColorSchemaImpl extends Converter implements TextColorSchema {

    private final String schema;

    public TextColorSchemaImpl(String schema) {
        super();
        this.schema = schema;
    }

    @Override
    public char convert(int color) {
        int value = (color + 1) / 32;
        char valueWin = switch (value) {
            case 1 -> '#';
            case 2 -> '$';
            case 3 -> '@';
            case 4 -> '%';
            case 5 -> '*';
            case 6 -> '+';
            case 7 -> '-';
            case 8 -> '\'';
            default -> throw new IllegalStateException("Unexpected value: " + value);
        };
        char valueIos = switch (value) {
            case 1 -> '▇';
            case 2 -> '●';
            case 3 -> '◉';
            case 4 -> '◍';
            case 5 -> '◎';
            case 6 -> '○';
            case 7 -> '◌';
            case 8 -> '-';
            default -> throw new IllegalStateException("Unexpected value: " + value);
        };
        if (schema.equals("Win")) {
            return valueWin;
        }
        if (schema.equals("Ios")) {
            return valueIos;
        }
        return 0; // TODO: если не соответствует ни одному из значений, нужно выдать что-то "более осмысленное"
    }

}
