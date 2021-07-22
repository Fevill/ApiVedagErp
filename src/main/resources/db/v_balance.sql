 SELECT journal.credit_id AS acc_id,
    sum(journal.amount) AS credit,
    0 AS debit,
    journal.namespace_id,
    journal.date_operation
   FROM journal
  GROUP BY journal.credit_id, journal.namespace_id, journal.date_operation
UNION
 SELECT journal.debit_id AS acc_id,
    0 AS credit,
    sum(journal.amount) AS debit,
    journal.namespace_id,
    journal.date_operation
   FROM journal
  GROUP BY journal.debit_id, journal.namespace_id, journal.date_operation
  ORDER BY 1;