INSERT INTO venue_type (venue_type) VALUES
("Bar"),
("Cinema"),
("Club"),
("Restaurant"),
("Theatre")
;

INSERT INTO location (loc) VALUES
("Dublin 1"),
("Dublin 2"),
("Dublin 3"),
("Dublin 4"),
("Dublin 5"),
("Dublin 6"),
("Dublin 6W"),
("Dublin 7"),
("Dublin 8"),
("Dublin 9"),
("Dublin 10"),
("Dublin 11"),
("Dublin 12"),
("Dublin 13"),
("Dublin 14"),
("Dublin 15"),
("Dublin 16"),
("Dublin 17"),
("Dublin 18"),
("Dublin 20"),
("Dublin 22"),
("Dublin 24"),
("Co. Dublin")
;

INSERT INTO venue (name, street_name, location, venue_type) VALUES
(
	"Porterhouse Central", 
	"Nassau Street", 
	(
		SELECT _id
		FROM location
		WHERE loc="Dublin 2"
	),
	(
		SELECT _id
		FROM venue_type
		WHERE venue_type="bar"
	)
),
(
	"Kennedy's",
	"Westland Row",
	(
		SELECT _id
		FROM location
		WHERE loc="Dublin 2"
	),
	(
		SELECT _id
		FROM venue_type
		WHERE venue_type="bar"
	)
),
(
	"Pacino's",
	"Suffolk Street",
	(
		SELECT _id
			FROM location
			WHERE loc="Dublin 2"
	),
	(
		SELECT _id
		FROM venue_type
		WHERE venue_type="restaurant"
	)
),
(
	"The Counter",
	"Suffolk Street",
	(
		SELECT _id
		FROM location
		WHERE loc="Dublin 2"
	),
	(
		SELECT _id
		FROM venue_type
		WHERE venue_type="restaurant"
	)
),
(
	"Tolteca",
	"Suffolk Street",
	(
		SELECT _id
		FROM location
		WHERE loc="Dublin 2"
	),
	(
		SELECT _id
		FROM venue_type
		WHERE venue_type="restaurant"
	)
),
(
	"Graham O'Sullivan's",
	"Artane",
	(
		SELECT _id
		FROM location
		WHERE loc="Dublin 5"
	),
	(
		SELECT _id
		FROM venue_type
		WHERE venue_type="restaurant"
	)
),
(
	"Subway",
	"Northside Shopping Centre",
	(
		SELECT _id
		FROM location
		WHERE loc="Dublin 5"
	),
	(
		SELECT _id
		FROM venue_type
		WHERE venue_type="restaurant"
	)
),
(
	"Subway",
	"Westmoreland Street",
	(
		SELECT _id
		FROM location
		WHERE loc="Dublin 2"
	),
	(
		SELECT _id
		FROM venue_type
		WHERE venue_type="restaurant"
	)
),
(
	"Subway",
	"O'Connell Street",
	(
		SELECT _id
		FROM location
		WHERE loc="Dublin 1"
	),
	(
		SELECT _id
		FROM venue_type
		WHERE venue_type="restaurant"
	)
),
(
	"Porterhouse Templebar",
	"Parliament Street",
	(
		SELECT _id
		FROM location
		WHERE loc="Dublin 2"
	),
	(
		SELECT _id
		FROM venue_type
		WHERE venue_type="bar"
	)
),
(
	"Zaytoon",
	"Parliament Street",
	(
		SELECT _id
		FROM location
		WHERE loc="Dublin 2"
	),
	(
		SELECT _id
		FROM venue_type
		WHERE venue_type="restaurant"
	)
),
(
	"McDonald's",
	"O'Connell Street Upper",
	(
		SELECT _id
		FROM location
		WHERE loc="Dublin 1"
	),
	(
		SELECT _id
		FROM venue_type
		WHERE venue_type="restaurant"
	)
),
(
	"McDonald's",
	"O'Connell Street Lower",
	(
		SELECT _id
		FROM location
		WHERE loc="Dublin 1"
	),
	(
		SELECT _id
		FROM venue_type
		WHERE venue_type="restaurant"
	)
),
(
	"Copper Face Jack's",
	"Harcourt Street",
	(
		SELECT _id
		FROM location
		WHERE loc="Dublin 2"
	),
	(
		SELECT _id
		FROM venue_type
		WHERE venue_type="club"
	)
),
(
	"Abbey Theatre",
	"Abbey Street Lower",
	(
		SELECT _id
		FROM location
		WHERE loc="Dublin 1"
	),
	(
		SELECT _id
		FROM venue_type
		WHERE venue_type="theatre"
	)
);

INSERT INTO rating (venue,
					approach,
					doors,
					flooring,
					steps,
					lifts,
					bathrooms,
					layout,
					staff,
					parking,
					sub_total)
VALUES
(
	(
		SELECT _id
		FROM venue
		WHERE name="Abbey Theatre"
	),
	5, 5, 5, 5, 5, 5, 5, 5, 5, 5
),
(
	(
		SELECT _id
		FROM venue
		WHERE name="Abbey Theatre"
	),
	1, 1, 1, 1, 1, 1, 1, 1, 1, 1
),
(
	(
		SELECT _id
		FROM venue
		WHERE name="Porterhouse Central"
	),
	5, 3, 4, 2, 1, 2, 3, 5, 2, 3
),
(
	(
		SELECT _id
		FROM venue
		WHERE name="Porterhouse Central"
	),
	5, 3, 3, 3, 1, 2, 3, 5, 2, 3
),
(
	(
		SELECT _id
		FROM venue
		WHERE name="Porterhouse Templebar"
	),
	5, 3, 4, 2, 1, 2, 1, 4, 1, 2
);
	
	