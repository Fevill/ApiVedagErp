SELECT c.number,
    c.label,
    c.namespace_id,
    j1.date_operation,
    c.category_id,
    COALESCE(sum(j1.amount), 0::real) AS debit
   FROM accounts c
     LEFT JOIN journal j1 ON j1.debit_id = c.id
  GROUP BY c.number, c.label, c.namespace_id, j1.date_operation, c.category_id;