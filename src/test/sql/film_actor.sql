-- desc
desc film_actor
;

-- count
SELECT COUNT(1)
FROM film_actor
;

-- select
SELECT *
FROM film_actor
;


-- actors with most films
EXPLAIN
SELECT a.*, COUNT(fa.film_id) AS films
FROM film_actor AS fa
         JOIN actor AS a ON a.actor_id = fa.actor_id
GROUP BY fa.actor_id
ORDER BY films DESC
LIMIT 5
;

-- films with most actors
EXPLAIN
SELECT fa.film_id, COUNT(fa.actor_id) AS actors, f.title
FROM film_actor AS fa
         JOIN film AS f ON f.film_id = fa.film_id
GROUP BY fa.film_id
ORDER BY actors DESC
LIMIT 5
;

-- actors played a role in specific film; 508
EXPLAIN
SELECT CONCAT_WS(', ', a.last_name, a.last_name) AS actor
FROM film_actor AS fa
         JOIN actor AS a ON fa.actor_id = a.actor_id
WHERE fa.film_id = :filmId
ORDER BY a.last_name ASC
;

-- films in which specific actor played a role; 107
EXPLAIN
SELECT CONCAT_WS(' ', f.title, CONCAT('(', f.release_year, ')')) AS film
FROM film_actor AS fa
         JOIN film f on f.film_id = fa.film_id
WHERE fa.actor_id = :actorId
ORDER BY f.title ASC
;

-- films of Sir Alec Guinness CH CBE
EXPLAIN
SELECT f.*
FROM film_actor AS fa
         JOIN actor AS a ON a.actor_id = fa.actor_id
         JOIN film AS f ON fa.film_id = f.film_id
WHERE a.last_name = 'Guinness'
  AND a.first_name = 'Alec'
;
