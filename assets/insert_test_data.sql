INSERT INTO venue_type (venue_type) VALUES
("bar"),
("cinema"),
("club"),
("restaurant"),
("theatre")
;

INSERT INTO location (loc) VALUES
("dublin 1"),
("dublin 2"),
("dublin 3"),
("dublin 4"),
("dublin 5"),
("dublin 6"),
("dublin 6w"),
("dublin 7"),
("dublin 8"),
("dublin 9"),
("dublin 10"),
("dublin 11"),
("dublin 12"),
("dublin 13"),
("dublin 14"),
("dublin 15"),
("dublin 16"),
("dublin 17"),
("dublin 18"),
("dublin 20"),
("dublin 22"),
("dublin 24"),
("co. dublin")
;

INSERT INTO venue (name, street_name, location, venue_type) VALUES
(
	"Porterhouse Central", 
	"Nassau Street", 
	(
		SELECT _id
		FROM location
		WHERE loc="dublin 2"
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
		WHERE loc="dublin 2"
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
			WHERE loc="dublin 2"
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
		WHERE loc="dublin 2"
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
		WHERE loc="dublin 2"
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
		WHERE loc="dublin 5"
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
		WHERE loc="dublin 5"
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
		WHERE loc="dublin 2"
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
		WHERE loc="dublin 1"
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
		WHERE loc="dublin 2"
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
		WHERE loc="dublin 2"
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
		WHERE loc="dublin 1"
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
		WHERE loc="dublin 1"
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
		WHERE loc="dublin 2"
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
		WHERE loc="dublin 1"
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
),
(
	(
		SELECT _id
		FROM venue
		WHERE name="Tolteca"
	),
	5, 3, 4, 2, 1, 2, 1, 4, 1, 2
);
	
	