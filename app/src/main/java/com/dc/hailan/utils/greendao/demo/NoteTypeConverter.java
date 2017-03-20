package com.dc.hailan.utils.greendao.demo;

import org.greenrobot.greendao.converter.PropertyConverter;

/**
 * Created by dc on 2017/3/14.
 *
 * PropertyConverter 接口  自带的
 */

public class NoteTypeConverter  implements PropertyConverter<NoteType, String> {
    @Override
    public NoteType convertToEntityProperty(String databaseValue) {
        return NoteType.valueOf(databaseValue);
    }

    @Override
    public String convertToDatabaseValue(NoteType entityProperty) {
        return entityProperty.name();
    }
}
