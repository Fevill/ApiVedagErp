SELECT bd.number,
    bd.label,
    bd.debit,
    bc.credit
   FROM v_balance_debit bd
     LEFT JOIN v_balance_credit bc ON bd.number::text = bc.number::text;