package com.salmon.test.models.dataload.pim.category;

        import lombok.AccessLevel;
        import lombok.Data;
        import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class CategoryPimModel {
    String catalogIdentifier;
    String categoryIdentifier;
    String delete;
    String sequence;
    String topGroup;
    String extraField;
}
