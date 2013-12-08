CREATE TABLE venue_type (
_id INTEGER PRIMARY KEY AUTOINCREMENT,
venue_type TEXT NOT NULL
);

CREATE TABLE location (
_id INTEGER PRIMARY KEY AUTOINCREMENT,
loc TEXT NOT NULL
);

CREATE TABLE venue (
_id INTEGER PRIMARY KEY AUTOINCREMENT,
name TEXT NOT NULL,
street_name TEXT NOT NULL,
location INTEGER,
venue_type INTEGER,
total_rating INTEGER,
FOREIGN KEY (location) REFERENCES location(_id),
FOREIGN KEY (venue_type) REFERENCES venue_type(_id)
);

CREATE TABLE rating (
_id INTEGER PRIMARY KEY AUTOINCREMENT,
venue INTEGER,
approach INTEGER(1),
doors INTEGER(1),
flooring INTEGER(1),
steps INTEGER(1),
lifts INTEGER(1),
bathrooms INTEGER(1),
layout INTEGER(1),
staff INTEGER(1),
parking INTEGER(1),
sub_total INTEGER(1),
FOREIGN KEY (venue) REFERENCES venue(_id)
);