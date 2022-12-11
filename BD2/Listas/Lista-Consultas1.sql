select nome_agencia,length(nome_agencia) 
	from agencia 
	where length(nome_agencia) = (select max(length(nome_agencia)) from agencia) 
	or length(nome_agencia) = (select min(length(nome_agencia)) from agencia); -- ok
	
-- Primeira lista de bd2
--01-Nomes das Agências com depósitos (nome pode aparecer repetido)

SELECT a.nome_agencia  FROM agencia a; -- ok

--02-Nomes das Agências com depósitos (nome sem repetição)

SELECT DISTINCT d.nome_agencia FROM deposito d; -- ok

--03-Nomes de Clientes com depósitos e empréstimos ao mesmo tempo;

SELECT DISTINCT d.nome_cliente 
	from deposito d  
		intersect 
			SELECT distinct e.nome_cliente  FROM emprestimo e; -- ok

--04-Nomes de Clientes com depósitos e empréstimos ao mesmo tempo na agência PUC;

SELECT DISTINCT d.nome_cliente  
	FROM deposito d  
		WHERE nome_agencia = 'PUC'  
		intersect 
			SELECT DISTINCT e.nome_cliente 
				FROM emprestimo as e
				where e.nome_agencia  = 'PUC'; -- ok

--Alternativa2 para incluir a condição uma única vez
SELECT distinct relatorio.nome_cliente FROM 
	(	SELECT DISTINCT d.nome_cliente, d.nome_agencia FROM deposito as d  
		INTERSECT 
		SELECT DISTINCT e.nome_cliente, e.nome_agencia FROM emprestimo as e
	) AS relatorio
WHERE relatorio.nome_agencia = 'PUC'; -- ok

--Alternativa3 para usar outro operador (IN)
SELECT DISTINCT d.nome_cliente 
	FROM deposito as d 
	WHERE d.nome_agencia = 'PUC'
	AND nome_cliente  in (SELECT DISTINCT nome_cliente 
			FROM emprestimo as d 
			WHERE nome_agencia='PUC'); -- ok

--05-Nomes de Clientes com depósitos, mas sem empréstimos na agência PUC;
SELECT d.nome_cliente
	FROM deposito as d
		where d.nome_agencia = 'PUC'  
		except 
		SELECT e.nome_cliente 
			from emprestimo as e 
			WHERE e.nome_agencia = 'PUC'; -- ok

SELECT DISTINCT d.nome_cliente
	from deposito as d
	WHERE d.nome_agencia = 'PUC' 
	AND d.nome_cliente NOT IN (SELECT DISTINCT e.nome_cliente FROM emprestimo as e WHERE e.nome_agencia = 'PUC'); -- ok

--06-Clientes que possuem depósitos ou empréstimos na agencia da PUC
SELECT DISTINCT d.nome_cliente
	from deposito as d 
	where d.nome_agencia = 'PUC'
	union (SELECT DISTINCT e.nome_cliente FROM emprestimo as e WHERE e.nome_agencia='PUC'); --ok

--07-Nomes de Clientes de Belo Horizonte

SELECT c.nome_cliente
	FROM Cliente as c
	where c.cidade_cliente = 'Belo Horizonte'; -- ok

--08-Nomes de clientes que tem 'Santos' no nome
SELECT c.nome_cliente
	from cliente as c 
	WHERE c.nome_cliente LIKE '%Santos%'; -- ok

--09-Nomes de clientes que tem terminam com 'Souza' ou 'Sousa'
SELECT c.nome_cliente
	FROM cliente as c
	WHERE c.nome_cliente like '%Sou_a'; -- ok

--10-Total de depósitos para cada nome de cliente
SELECT d.nome_cliente, sum(1)
	FROM deposito as d
	GROUP BY d.nome_cliente; -- ok

--11-Nomes de clientes e depósitos que estão entre 
--R$ 3.000,00 e R$ 4.000,00
SELECT d.nome_cliente, d.saldo_deposito
	FROM deposito as d 
	WHERE d.saldo_deposito >= 3000 
	AND  d.saldo_deposito <= 4000; -- ok

SELECT d.nome_cliente, d.saldo_deposito
	FROM deposito as d
	WHERE saldo_deposito BETWEEN 3000 and 4000; -- ok

--12-Nomes de clientes e a soma de depósitos de cada nome, 
--quando a soma for maior que R$ 5.000,00
SELECT d.nome_cliente, sum(d.saldo_deposito)
	FROM deposito as d 
	GROUP BY d.nome_cliente
	HAVING sum(d.saldo_deposito) > 5000; -- ok

--13-Nomes de clientes e a soma de depósitos de cada nome,
--quando a soma estiver entre R$ 1.000,00 e R$ 4.000,00,
--em ordem pela soma
SELECT d.nome_cliente, sum(d.saldo_deposito)
	FROM deposito as d 
	group by d.nome_cliente
	HAVING sum(d.saldo_deposito) BETWEEN 1000 AND 4000 
	ORDER BY sum(d.saldo_deposito); -- ok


--14-Nomes de clientes e a soma de depósitos de cada nome,
--quando a soma estiver maior do que a média de depósitos,
--em ordem pela soma

SELECT nome_cliente, sum(saldo_deposito)
	FROM deposito GROUP BY nome_cliente
		HAVING sum(saldo_deposito) > avg(saldo_deposito)
		ORDER BY sum(saldo_deposito); -- ok

--15-Faça uma listagem da soma de depósitos feitos
--por cada cliente em cada agência. Ordene o resultado
--pelo nome do cliente e depois pelo nome da agência.

SELECT nome_agencia, nome_cliente, sum(saldo_deposito)
	FROM deposito 
		GROUP BY nome_cliente, nome_agencia 
		ORDER BY nome_cliente, nome_agencia; -- ok

--16- Selecione os nomes dos clientes e a soma de depósitos
--feitos por eles em cada agência que foram maiores que todos
--os depósitos efetuados na agência 'Pampulha'.

--Conjunto1:
SELECT nome_cliente, nome_agencia, sum(saldo_deposito) 
	FROM deposito
	GROUP BY nome_cliente, nome_agencia; -- ok

--Conjunto2: 
SELECT sum(saldo_deposito) 
	FROM deposito 
	where nome_agencia = 'Pampulha' 
	GROUP BY nome_agencia; -- ok

--Conjunto1 HAVING sum > ALL Conjunto2
SELECT nome_cliente, nome_agencia, sum(saldo_deposito) 
	FROM deposito 
	GROUP BY nome_cliente, nome_agencia 
		HAVING sum(saldo_deposito) > all (SELECT sum(saldo_deposito) FROM deposito 
			where nome_agencia  = 'Pampulha'
			GROUP BY nome_agencia); -- ok

