package finance.domain;

import finance.importexport.DataExportVisitor;

public interface Visitable {
    void accept(DataExportVisitor visitor);
}
