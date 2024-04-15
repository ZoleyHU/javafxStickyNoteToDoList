create table if not exists StickyNotes (
    ID integer primary key AUTOINCREMENT,
    DESCRIPTION text not null,
    POSTPONES integer not null,
    DEADLINE text not null,
    BACKGROUND text not null
);