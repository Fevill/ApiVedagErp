SELECT 
j.id,j.amount as amount, 0 as amountprev, j.date_operation,
j.label,j.credit_id,j.debit_id,j.namespace_id
FROM public.journal j
UNION
SELECT 
jp.id,0 as amount, jp.amount as amountprev, jp.date_operation,
jp.label,jp.credit_id,jp.debit_id,jp.namespace_id
FROM public.journalprev jp