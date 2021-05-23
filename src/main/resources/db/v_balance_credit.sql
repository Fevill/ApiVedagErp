SELECT c.number,c.label,
COALESCE(SUM(j2.amount),0) as credit
FROM accounts AS c
LEFT JOIN journal AS j2 ON j2.credit_id = c.id
GROUP BY c.label,c.number ORDER BY c.number