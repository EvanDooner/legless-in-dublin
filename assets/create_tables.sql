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
approach REAL,
doors REAL,
flooring REAL,
steps REAL,
lifts REAL,
bathrooms REAL,
layout REAL,
staff REAL,
parking REAL,
total_rating REAL,
num_ratings INTEGER,
FOREIGN KEY (location) REFERENCES location(_id),
FOREIGN KEY (venue_type) REFERENCES venue_type(_id)
);

CREATE TABLE rating (
_id INTEGER PRIMARY KEY AUTOINCREMENT,
venue INTEGER,
approach REAL,
doors REAL,
flooring REAL,
steps REAL,
lifts REAL,
bathrooms REAL,
layout REAL,
staff REAL,
parking REAL,
sub_total REAL,
FOREIGN KEY (venue) REFERENCES venue(_id)
);