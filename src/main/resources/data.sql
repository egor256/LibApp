INSERT INTO users SELECT * FROM (
    SELECT 'egor', '0000', 'PRE_AUTH_USER' UNION
    SELECT 'tom', '0000', 'PRE_AUTH_LIBRARIAN'
) x WHERE NOT EXISTS(SELECT * FROM users);

INSERT INTO books SELECT * FROM (
    SELECT 1, 'Book1', 'Author1' UNION
    SELECT 2, 'Book2', 'Author2' UNION
    SELECT 3, 'Book3', 'Author3' UNION
    SELECT 4, 'Book4', 'Author4' UNION
    SELECT 5, 'Book5', 'Author5' UNION
    SELECT 6, 'Book6', 'Author6' UNION
    SELECT 7, 'Book7', 'Author7'
) x WHERE NOT EXISTS(SELECT * FROM books);
