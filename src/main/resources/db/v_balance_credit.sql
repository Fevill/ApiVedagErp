 SELECT c.number,
    c.label,
    c.category_id,
    COALESCE(sum(j2.amount), 0::real) AS credit
   FROM accounts c
     LEFT JOIN journal j2 ON j2.credit_id = c.id
  GROUP BY c.label, c.number, c.category_id
  ORDER BY c.number;