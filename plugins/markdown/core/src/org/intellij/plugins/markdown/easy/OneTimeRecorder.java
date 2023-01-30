package org.intellij.plugins.markdown.easy;

import com.intellij.openapi.editor.Document;

import java.util.concurrent.atomic.AtomicReference;

public class OneTimeRecorder {

    private static AtomicReference<Record> RECORD = new AtomicReference<>();

    public static void push(Record record) {
        RECORD.set(record);
    }

    public static Record pop() {
        Record record = RECORD.get();
        RECORD.set(null);
        return record;
    }


    public static final class Record {
        public int offset;
        public String text;
        public Document doc;

        public int selectedStart;
        public int selectedEnd;
    }
}
