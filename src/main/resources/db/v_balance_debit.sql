SELECT c.number,c.label,
COALESCE(SUM(j1.amount),0) as debit
FROM accounts AS c
LEFT JOIN journal AS j1 ON j1.debit_id = c.id
GROUP BY c.label,c.number ORDER BY c.number