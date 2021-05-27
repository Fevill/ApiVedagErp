 SELECT COALESCE(bd.date_operation, bc.date_operation) AS date_operation,
    bd.number,
    bd.label,
    bd.namespace_id AS bdid,
    bc.namespace_id AS bcid,
    bd.category_id,
    bd.debit,
    bc.credit
   FROM v_balance_debit bd
     LEFT JOIN v_balance_credit bc ON bd.number::text = bc.number::text
  ORDER BY bd.number;