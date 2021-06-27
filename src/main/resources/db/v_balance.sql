 SELECT b1.number,
    b1.label,
    b1.namespace_id,
    b1.date_operation,
    b1.category_id,
    b1.credit,
    0 AS debit
   FROM v_balance_credit b1
UNION
 SELECT b2.number,
    b2.label,
    b2.namespace_id,
    b2.date_operation,
    b2.category_id,
    0 AS credit,
    b2.debit
   FROM v_balance_debit b2;