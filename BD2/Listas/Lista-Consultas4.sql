--01-Nome e cidade de clientes que possuem algum emprestimo, ordenado pelo nome do cliente
-- do prof
SELECT DISTINCT c.nome_cliente, c.cidade_cliente
	FROM emprestimo e, cliente c
	where e.nome_cliente  = c.nome_cliente 
	ORDER BY c.nome_cliente; -- ok

-- usando join
SELECT DISTINCT c.nome_cliente, c.cidade_cliente
	FROM emprestimo e right join cliente c on e.nome_cliente = c.nome_cliente
	ORDER BY nome_cliente; -- ok

--02-Nome e cidade de clientes que possuem emprestimo na PUC, ordenado pelo nome do cliente
SELECT DISTINCT c.nome_cliente, c.cidade_cliente
	FROM cliente as c , emprestimo as e 
		WHERE c.nome_cliente = e.nome_cliente
		and  e.nome_agencia = 'PUC'
		ORDER BY c.nome_cliente ; -- ok
		
-- usando natual join
SELECT DISTINCT c.nome_cliente, c.cidade_cliente
	FROM cliente as c natural join emprestimo  as e 
		where e.nome_agencia = 'PUC'
		ORDER BY c.nome_cliente ; -- ok
		
--03-Nomes de Clientes com saldo entre 100 e 900
SELECT c.nome_cliente
	FROM Cliente as c, Deposito as d
		WHERE c.nome_cliente = d.nome_cliente
		AND D.saldo_deposito between 100 and 900; -- ok

--04-Nomes de clientes, valores de depósitos e empréstimos na agencia PUC
SELECT d.nome_cliente, d.saldo_deposito, e.valor_emprestimo, e.nome_agencia
	FROM deposito as d, emprestimo as e
		WHERE d.nome_agencia = 'PUC'
		and d.nome_agencia  = e.nome_agencia 
		and d.nome_cliente = e.nome_cliente; -- ok

﻿--05-Selecione os nomes dos clientes da cidade de Contagem com depósitos maiores do que R$ 3.000,00

select c.nome_cliente, c.cidade_cliente, d.saldo_deposito
	from cliente as c, deposito as d
	where  c.cidade_cliente = 'Contagem'
	and c.nome_cliente = d.nome_cliente 
	and d.saldo_deposito > 3000; -- ok

--06-Um gerente pretende criar uma lista de clientes que correm o risco
--de se individar de maneira irreversível. Para tanto ele formulou
--a seguinte pesquisa:
--Selecione os clientes da cidade de Santa Luzia com depósitos
--menores do que R$ 1.000,00 e emprestimos maiores que R$ 1.000,00
select c.nome_cliente, c.cidade_cliente, d.saldo_deposito, e.valor_emprestimo
	from cliente as c, deposito as d, emprestimo as e 
		where c.nome_cliente = d.nome_cliente
		and c.nome_cliente = e.nome_cliente 
		and c.cidade_cliente = 'Santa Luzia'
		and d.saldo_deposito < 1000
		and e.valor_emprestimo > 1000; -- ok

-- usando join
select c.nome_cliente, c.cidade_cliente, d.saldo_deposito, e.valor_emprestimo
	from cliente as c join deposito as d on c.nome_cliente = d.nome_cliente 
		join emprestimo e on c.nome_cliente = e.nome_cliente 
	where  c.cidade_cliente = 'Santa Luzia'
	and d.saldo_deposito < 1000
	and e.valor_emprestimo > 1000; -- ok
	
--07-Um gerente pretende criar uma lista de clientes que correm o risco
--de se individar de maneira irreversível. Para tanto ele formulou
--a seguinte pesquisa:
--Selecione os clientes da cidade de Santa Luzia com uma média de 
--depósitos menor do que a média de empréstimos

select c.nome_cliente , sum(d.saldo_deposito), sum(e.valor_emprestimo)
	from cliente as c, deposito as d, emprestimo as e
	where c.nome_cliente = d.nome_cliente
	and c.nome_cliente  = e.nome_cliente 
	and c.cidade_cliente = 'Santa Luzia'
	group by c.nome_cliente
	having avg(d.saldo_deposito) <  avg(e.valor_emprestimo); -- não entendi!

--08-É preciso atualizar a informação do saldo do cliente na tabela Conta.
--para este propósito devemos levar em conta o saldo dos depósitos menos os
--saldos de empréstimos. o cálculo final deve ser armazenado na tabela conta.

UPDATE conta SET SALDO_CONTA = 0;

SELECT NOME_CLIENTE, SALDO_CONTA FROM CONTA ORDER BY SALDO_CONTA DESC;

SELECT NUMERO_CONTA, NOME_AGENCIA, NOME_CLIENTE, SUM(SALDO_DEPOSITO)
	FROM DEPOSITO 
	GROUP BY NUMERO_CONTA, NOME_AGENCIA, NOME_CLIENTE;

--Primeiro os clientes que possuem deposito e emprestimos (ambos)

SELECT	e.NOME_CLIENTE, e.NOME_AGENCIA, e.NUMERO_CONTA, SUM(d.SALDO_DEPOSITO), SUM(e.VALOR_EMPRESTIMO), 
		SUM(d.SALDO_DEPOSITO) - SUM(e.VALOR_EMPRESTIMO) AS TOTAL
	FROM EMPRESTIMO AS e, DEPOSITO AS d
	WHERE e.NOME_CLIENTE = d.NOME_CLIENTE
	GROUP BY e.NOME_CLIENTE, e.NOME_AGENCIA, e.NUMERO_CONTA; -- ok

--Atualiza contas que possuem deposito e emprestimos (ambos)

UPDATE CONTA as c SET saldo_conta  = RELATORIO.TOTAL
	FROM(
		select e.NOME_CLIENTE, e.NOME_AGENCIA, e.NUMERO_CONTA, SUM(d.SALDO_DEPOSITO), SUM(e.VALOR_EMPRESTIMO), 
				SUM(d.SALDO_DEPOSITO) - SUM(e.VALOR_EMPRESTIMO) AS TOTAL
			FROM EMPRESTIMO as e, DEPOSITO as d
			GROUP BY e.NOME_CLIENTE, e.NOME_AGENCIA, e.NUMERO_CONTA
	) AS RELATORIO
	WHERE c.NOME_CLIENTE = RELATORIO.NOME_CLIENTE
	AND   c.NOME_AGENCIA = RELATORIO.NOME_AGENCIA
	AND   c.NUMERO_CONTA = RELATORIO.NUMERO_CONTA; -- ok
