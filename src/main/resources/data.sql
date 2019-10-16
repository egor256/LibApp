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

INSERT INTO tags SELECT * FROM (
    SELECT 1, 'science' UNION
    SELECT 2, 'history' UNION
    SELECT 3, 'fiction' UNION
    SELECT 4, 'poetry' UNION
    SELECT 5, 'drama' UNION
    SELECT 6, 'thriller' UNION
    SELECT 7, 'classic' UNION
    SELECT 8, 'biography'
) x WHERE NOT EXISTS(SELECT * FROM tags);

INSERT INTO bookTags SELECT * FROM (
    SELECT 1 AS bookId, 1 AS tagId UNION
    SELECT 1 AS bookId, 2 AS tagId UNION
    SELECT 1 AS bookId, 9 AS tagId UNION
    SELECT 2 AS bookId, 8 AS tagId UNION
    SELECT 2 AS bookId, 3 AS tagId UNION
    SELECT 3 AS bookId, 3 AS tagId UNION
    SELECT 4 AS bookId, 4 AS tagId UNION
    SELECT 5 AS bookId, 5 AS tagId UNION
    SELECT 6 AS bookId, 7 AS tagId UNION
    SELECT 7 AS bookId, 4 AS tagId
) x WHERE NOT EXISTS(SELECT * FROM bookTags);
