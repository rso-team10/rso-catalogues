INSERT INTO artist(alias, artisttype, birthdate, deathdate, gender, name) select 'fredi', 'PERSON', '1995-10-10 00:00:00', NULL, 'MAN', 'Fredi Miler' where not exists(select id from artist where alias = 'fredi')

INSERT INTO album(name, artist_id) (select 'Mi gremo v hribe', artist.id from artist where artist.alias = 'fredi')

INSERT INTO track(artist_id, album_id, name, genre, active) (select art.id, alb.id, 'Hojladri1', 'Rock', true from artist art join album alb on art.id = alb.artist_id where art.alias = 'fredi')
INSERT INTO track(artist_id, album_id, name, genre, active) (select art.id, alb.id, 'Hojladri2', 'Rock', true from artist art join album alb on art.id = alb.artist_id where art.alias = 'fredi')
INSERT INTO track(artist_id, album_id, name, genre, active) (select art.id, alb.id, 'Hojladri3', 'Rock', true from artist art join album alb on art.id = alb.artist_id where art.alias = 'fredi')