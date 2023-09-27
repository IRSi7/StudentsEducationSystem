package space.irsi7.enums;

public enum PathsEnum {
    STUDENTS("data/students.yaml"),
    EDUPLANS("src/main/resources/data/eduplans.yaml"),
    THEMES("src/main/resources/data/themes.yaml"),

    CONFIG("data/properties.yaml");

    public String getPath() {
        return path;
    }

    private final String path;

    PathsEnum(String path) {
        this.path = path;
    }
}
