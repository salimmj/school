mapreduce:
	addi $sp, $sp, -24
	sw $ra, 0($sp)
	sw $s0, 4($sp)
	sw $s1, 8($sp)
	sw $s2, 12($sp)
	sw $s3, 16($sp)

	move $s0, $a0
	move $s1, $a1
	move $s2, $a2

	lw $t0, 0($s0)
	beqz $t0, recurse

	lw $a0, 4($s0)
	jalr $a1

	lw $ra, 0($sp)
	lw $s0, 4($sp)
	lw $s1, 8($sp)
	lw $s2, 12($sp)
	lw $s3, 16($sp)
	addi $sp, $sp, 24

	jr $ra

recurse:
	lw $a0, 4($s0)
	ori $a1, $s1, 0
	ori $a2, $s2, 0
	jal mapreduce
	move $s3, $v0
	lw $a0, 8($s0)
	
	ori $a1, $s1, 0
	ori $a2, $s2, 0
	jal mapreduce
	move $a0, $s3
	move $a1, $v0

	jalr $s2
	lw $ra, 0($sp)
	lw $s0, 4($sp)
	lw $s1, 8($sp)
	lw $s2, 12($sp)
	lw $s3, 16($sp)
	addi $sp, $sp, 24
	jr $ra

#############################################################################
##                   Do not remove this separator.                         ##
#############################################################################

	
main:     
	# mapreduce(tiny,unit,sum) = 4
	la $a0, tiny
	la $a1, unit
    la $a2, sum
    jal mapreduce
    li $a0, 4
    move $a1, $v0
    jal print_result

	# mapreduce(lorem,unit,sum) = 64
	la $a0, lorem
	la $a1, unit
	la $a2, sum
	jal mapreduce
	li $a0, 64
	move $a1, $v0
	jal print_result

	# mapreduce(tiny,unit,min) = 1
	la $a0, tiny
	la $a1, unit
	la $a2, min
	jal mapreduce
	li $a0, 1
	move $a1, $v0
	jal print_result

	# mapreduce(lorem,unit,min) = 1
	la $a0, lorem
	la $a1, unit
	la $a2, min
	jal mapreduce
	li $a0, 1
	move $a1, $v0
	jal print_result

	# mapreduce(tiny,unit,max) = 1
	la $a0, tiny
	la $a1, unit
	la $a2, max
	jal mapreduce
	li $a0, 1
	move $a1, $v0
	jal print_result

	# mapreduce(lorem,unit,max) = 1
	la $a0, lorem
	la $a1, unit
	la $a2, max
	jal mapreduce
	li $a0, 1
	move $a1, $v0
	jal print_result

	# mapreduce(tiny,strlen,sum) = 16
	la $a0, tiny
	la $a1, strlen
	la $a2, sum
	jal mapreduce
	li $a0, 16
	move $a1, $v0
	jal print_result

	# mapreduce(lorem,strlen,sum) = 354
	la $a0, lorem
	la $a1, strlen
	la $a2, sum
	jal mapreduce
	li $a0, 354
	move $a1, $v0
	jal print_result

	# mapreduce(tiny,strlen,min) = 3
	la $a0, tiny
	la $a1, strlen
	la $a2, min
	jal mapreduce
	li $a0, 3
	move $a1, $v0
	jal print_result

	# mapreduce(lorem,strlen,min) = 2
	la $a0, lorem
	la $a1, strlen
	la $a2, min
	jal mapreduce
	li $a0, 2
	move $a1, $v0
	jal print_result

	# mapreduce(tiny,strlen,max) = 5
	la $a0, tiny
	la $a1, strlen
	la $a2, max
	jal mapreduce
	li $a0, 5
	move $a1, $v0
	jal print_result

	# mapreduce(lorem,strlen,max) = 13
	la $a0, lorem
	la $a1, strlen
	la $a2, max
	jal mapreduce
	li $a0, 13
	move $a1, $v0
	jal print_result
	
	li $v0, 10                  # exit
	syscall	
strlen:	
	li $v0, 0
strlen_top:
	lbu $t0, 0($a0)
	beqz $t0, strlen_done
	addi $v0, $v0, 1
	addi $a0, $a0, 1
	b strlen_top
strlen_done:
	jr $ra

unit:
	li $v0, 1
	jr $ra

sum:	
	add $v0, $a0, $a1
	jr $ra

min:
	slt $t0, $a0, $a1
	beqz $t0, min_a1
	move $v0, $a0
	jr $ra
min_a1:	move $v0, $a1
	jr $ra

max:
	slt $t0, $a1, $a0
	beqz $t0, max_a1
	move $v0, $a0
	jr $ra
max_a1:	move $v0, $a1
	jr $ra
print_result:
	addi $sp, $sp, -12
	sw $ra, 0($sp)
	sw $s0, 4($sp)
	sw $s1, 8($sp)

	# save expected and actual
	move $s0, $a0
	move $s1, $a1
	
	# compare expected and actual
	beq $s0, $s1, print_result_pass

	# test failed
	la $a0, fail_msg
	jal print_string
	b print_result_report

print_result_pass:	
	# test passed
	la $a0, pass_msg
	jal print_string

print_result_report:	
	# print report
	la $a0, report_msg1
	jal print_string
	move $a0, $s0
	jal print_int
	la $a0, report_msg2
	jal print_string	
	move $a0, $s1
	jal print_int
	la $a0, report_msg3
	jal print_string
	
	# restore state
	lw $ra, 0($sp)
	lw $s0, 4($sp)
	lw $s1, 8($sp)
	addi $sp, $sp, 12

	# return
	jr $ra

print_int:
	# a0: integer to print
	li $v0, 1
	syscall
	jr $ra

print_newline:
 	li $v0, 11
 	li $a0, 10
 	syscall
	jr $ra

print_string:
	li $v0, 4
	syscall
	jr $ra
	
print_space:
	li $a0, 32
	li $v0, 11
	syscall
	jr $ra
.data

pass_msg:		.asciiz "Pass "
fail_msg:		.asciiz "Fail "
report_msg1:	.asciiz "(expecting "
report_msg2:	.asciiz ", got "
report_msg3:	.asciiz ")\n"
	
tiny:	 .word 0 tinyL tinyR
tinyL:	 .word 0 tinyLL tinyLR
tinyR:	 .word 0 tinyRL tinyRR
tinyLL:	 .word 1 tinyLL_str
tinyLR:	 .word 1 tinyLR_str
tinyRL:	 .word 1 tinyRL_str
tinyRR:	 .word 1 tinyRR_str
tinyLL_str:	 .asciiz "itty"
tinyLR_str:	 .asciiz "bitty"
tinyRL_str:	 .asciiz "word"
tinyRR_str:	 .asciiz "set"

lorem:	 .word 0 loremL loremR
loremL:	 .word 0 loremLL loremLR
loremR:	 .word 0 loremRL loremRR
loremLL:	 .word 0 loremLLL loremLLR
loremLR:	 .word 0 loremLRL loremLRR
loremRL:	 .word 0 loremRLL loremRLR
loremRR:	 .word 0 loremRRL loremRRR
loremLLL:	 .word 0 loremLLLL loremLLLR
loremLLR:	 .word 0 loremLLRL loremLLRR
loremLRL:	 .word 0 loremLRLL loremLRLR
loremLRR:	 .word 0 loremLRRL loremLRRR
loremRLL:	 .word 0 loremRLLL loremRLLR
loremRLR:	 .word 0 loremRLRL loremRLRR
loremRRL:	 .word 0 loremRRLL loremRRLR
loremRRR:	 .word 0 loremRRRL loremRRRR
loremLLLL:	 .word 0 loremLLLLL loremLLLLR
loremLLLR:	 .word 0 loremLLLRL loremLLLRR
loremLLRL:	 .word 0 loremLLRLL loremLLRLR
loremLLRR:	 .word 0 loremLLRRL loremLLRRR
loremLRLL:	 .word 0 loremLRLLL loremLRLLR
loremLRLR:	 .word 0 loremLRLRL loremLRLRR
loremLRRL:	 .word 0 loremLRRLL loremLRRLR
loremLRRR:	 .word 0 loremLRRRL loremLRRRR
loremRLLL:	 .word 0 loremRLLLL loremRLLLR
loremRLLR:	 .word 0 loremRLLRL loremRLLRR
loremRLRL:	 .word 0 loremRLRLL loremRLRLR
loremRLRR:	 .word 0 loremRLRRL loremRLRRR
loremRRLL:	 .word 0 loremRRLLL loremRRLLR
loremRRLR:	 .word 0 loremRRLRL loremRRLRR
loremRRRL:	 .word 0 loremRRRLL loremRRRLR
loremRRRR:	 .word 0 loremRRRRL loremRRRRR
loremLLLLL:	 .word 0 loremLLLLLL loremLLLLLR
loremLLLLR:	 .word 0 loremLLLLRL loremLLLLRR
loremLLLRL:	 .word 0 loremLLLRLL loremLLLRLR
loremLLLRR:	 .word 0 loremLLLRRL loremLLLRRR
loremLLRLL:	 .word 0 loremLLRLLL loremLLRLLR
loremLLRLR:	 .word 0 loremLLRLRL loremLLRLRR
loremLLRRL:	 .word 0 loremLLRRLL loremLLRRLR
loremLLRRR:	 .word 0 loremLLRRRL loremLLRRRR
loremLRLLL:	 .word 0 loremLRLLLL loremLRLLLR
loremLRLLR:	 .word 0 loremLRLLRL loremLRLLRR
loremLRLRL:	 .word 0 loremLRLRLL loremLRLRLR
loremLRLRR:	 .word 0 loremLRLRRL loremLRLRRR
loremLRRLL:	 .word 0 loremLRRLLL loremLRRLLR
loremLRRLR:	 .word 0 loremLRRLRL loremLRRLRR
loremLRRRL:	 .word 0 loremLRRRLL loremLRRRLR
loremLRRRR:	 .word 0 loremLRRRRL loremLRRRRR
loremRLLLL:	 .word 0 loremRLLLLL loremRLLLLR
loremRLLLR:	 .word 0 loremRLLLRL loremRLLLRR
loremRLLRL:	 .word 0 loremRLLRLL loremRLLRLR
loremRLLRR:	 .word 0 loremRLLRRL loremRLLRRR
loremRLRLL:	 .word 0 loremRLRLLL loremRLRLLR
loremRLRLR:	 .word 0 loremRLRLRL loremRLRLRR
loremRLRRL:	 .word 0 loremRLRRLL loremRLRRLR
loremRLRRR:	 .word 0 loremRLRRRL loremRLRRRR
loremRRLLL:	 .word 0 loremRRLLLL loremRRLLLR
loremRRLLR:	 .word 0 loremRRLLRL loremRRLLRR
loremRRLRL:	 .word 0 loremRRLRLL loremRRLRLR
loremRRLRR:	 .word 0 loremRRLRRL loremRRLRRR
loremRRRLL:	 .word 0 loremRRRLLL loremRRRLLR
loremRRRLR:	 .word 0 loremRRRLRL loremRRRLRR
loremRRRRL:	 .word 0 loremRRRRLL loremRRRRLR
loremRRRRR:	 .word 0 loremRRRRRL loremRRRRRR
loremLLLLLL:	 .word 1 loremLLLLLL_str
loremLLLLLR:	 .word 1 loremLLLLLR_str
loremLLLLRL:	 .word 1 loremLLLLRL_str
loremLLLLRR:	 .word 1 loremLLLLRR_str
loremLLLRLL:	 .word 1 loremLLLRLL_str
loremLLLRLR:	 .word 1 loremLLLRLR_str
loremLLLRRL:	 .word 1 loremLLLRRL_str
loremLLLRRR:	 .word 1 loremLLLRRR_str
loremLLRLLL:	 .word 1 loremLLRLLL_str
loremLLRLLR:	 .word 1 loremLLRLLR_str
loremLLRLRL:	 .word 1 loremLLRLRL_str
loremLLRLRR:	 .word 1 loremLLRLRR_str
loremLLRRLL:	 .word 1 loremLLRRLL_str
loremLLRRLR:	 .word 1 loremLLRRLR_str
loremLLRRRL:	 .word 1 loremLLRRRL_str
loremLLRRRR:	 .word 1 loremLLRRRR_str
loremLRLLLL:	 .word 1 loremLRLLLL_str
loremLRLLLR:	 .word 1 loremLRLLLR_str
loremLRLLRL:	 .word 1 loremLRLLRL_str
loremLRLLRR:	 .word 1 loremLRLLRR_str
loremLRLRLL:	 .word 1 loremLRLRLL_str
loremLRLRLR:	 .word 1 loremLRLRLR_str
loremLRLRRL:	 .word 1 loremLRLRRL_str
loremLRLRRR:	 .word 1 loremLRLRRR_str
loremLRRLLL:	 .word 1 loremLRRLLL_str
loremLRRLLR:	 .word 1 loremLRRLLR_str
loremLRRLRL:	 .word 1 loremLRRLRL_str
loremLRRLRR:	 .word 1 loremLRRLRR_str
loremLRRRLL:	 .word 1 loremLRRRLL_str
loremLRRRLR:	 .word 1 loremLRRRLR_str
loremLRRRRL:	 .word 1 loremLRRRRL_str
loremLRRRRR:	 .word 1 loremLRRRRR_str
loremRLLLLL:	 .word 1 loremRLLLLL_str
loremRLLLLR:	 .word 1 loremRLLLLR_str
loremRLLLRL:	 .word 1 loremRLLLRL_str
loremRLLLRR:	 .word 1 loremRLLLRR_str
loremRLLRLL:	 .word 1 loremRLLRLL_str
loremRLLRLR:	 .word 1 loremRLLRLR_str
loremRLLRRL:	 .word 1 loremRLLRRL_str
loremRLLRRR:	 .word 1 loremRLLRRR_str
loremRLRLLL:	 .word 1 loremRLRLLL_str
loremRLRLLR:	 .word 1 loremRLRLLR_str
loremRLRLRL:	 .word 1 loremRLRLRL_str
loremRLRLRR:	 .word 1 loremRLRLRR_str
loremRLRRLL:	 .word 1 loremRLRRLL_str
loremRLRRLR:	 .word 1 loremRLRRLR_str
loremRLRRRL:	 .word 1 loremRLRRRL_str
loremRLRRRR:	 .word 1 loremRLRRRR_str
loremRRLLLL:	 .word 1 loremRRLLLL_str
loremRRLLLR:	 .word 1 loremRRLLLR_str
loremRRLLRL:	 .word 1 loremRRLLRL_str
loremRRLLRR:	 .word 1 loremRRLLRR_str
loremRRLRLL:	 .word 1 loremRRLRLL_str
loremRRLRLR:	 .word 1 loremRRLRLR_str
loremRRLRRL:	 .word 1 loremRRLRRL_str
loremRRLRRR:	 .word 1 loremRRLRRR_str
loremRRRLLL:	 .word 1 loremRRRLLL_str
loremRRRLLR:	 .word 1 loremRRRLLR_str
loremRRRLRL:	 .word 1 loremRRRLRL_str
loremRRRLRR:	 .word 1 loremRRRLRR_str
loremRRRRLL:	 .word 1 loremRRRRLL_str
loremRRRRLR:	 .word 1 loremRRRRLR_str
loremRRRRRL:	 .word 1 loremRRRRRL_str
loremRRRRRR:	 .word 1 loremRRRRRR_str
loremLLLLLL_str:	 .asciiz "Lorem"
loremLLLLLR_str:	 .asciiz "ipsum"
loremLLLLRL_str:	 .asciiz "dolor"
loremLLLLRR_str:	 .asciiz "sit"
loremLLLRLL_str:	 .asciiz "amet,"
loremLLLRLR_str:	 .asciiz "consectetur"
loremLLLRRL_str:	 .asciiz "adipiscing"
loremLLLRRR_str:	 .asciiz "elit,"
loremLLRLLL_str:	 .asciiz "sed"
loremLLRLLR_str:	 .asciiz "do"
loremLLRLRL_str:	 .asciiz "eiusmod"
loremLLRLRR_str:	 .asciiz "tempor"
loremLLRRLL_str:	 .asciiz "incididunt"
loremLLRRLR_str:	 .asciiz "ut"
loremLLRRRL_str:	 .asciiz "labore"
loremLLRRRR_str:	 .asciiz "et"
loremLRLLLL_str:	 .asciiz "dolore"
loremLRLLLR_str:	 .asciiz "magna"
loremLRLLRL_str:	 .asciiz "aliqua."
loremLRLLRR_str:	 .asciiz "Ut"
loremLRLRLL_str:	 .asciiz "enim"
loremLRLRLR_str:	 .asciiz "ad"
loremLRLRRL_str:	 .asciiz "minim"
loremLRLRRR_str:	 .asciiz "veniam,"
loremLRRLLL_str:	 .asciiz "quis"
loremLRRLLR_str:	 .asciiz "nostrud"
loremLRRLRL_str:	 .asciiz "exercitation"
loremLRRLRR_str:	 .asciiz "ullamco"
loremLRRRLL_str:	 .asciiz "laboris"
loremLRRRLR_str:	 .asciiz "nisi"
loremLRRRRL_str:	 .asciiz "ut"
loremLRRRRR_str:	 .asciiz "aliquip"
loremRLLLLL_str:	 .asciiz "ex"
loremRLLLLR_str:	 .asciiz "ea"
loremRLLLRL_str:	 .asciiz "commodo"
loremRLLLRR_str:	 .asciiz "consequat."
loremRLLRLL_str:	 .asciiz "Duis"
loremRLLRLR_str:	 .asciiz "aute"
loremRLLRRL_str:	 .asciiz "irure"
loremRLLRRR_str:	 .asciiz "dolor"
loremRLRLLL_str:	 .asciiz "in"
loremRLRLLR_str:	 .asciiz "reprehenderit"
loremRLRLRL_str:	 .asciiz "in"
loremRLRLRR_str:	 .asciiz "voluptate"
loremRLRRLL_str:	 .asciiz "velit"
loremRLRRLR_str:	 .asciiz "esse"
loremRLRRRL_str:	 .asciiz "cillum"
loremRLRRRR_str:	 .asciiz "dolore"
loremRRLLLL_str:	 .asciiz "eu"
loremRRLLLR_str:	 .asciiz "fugiat"
loremRRLLRL_str:	 .asciiz "nulla"
loremRRLLRR_str:	 .asciiz "pariatur."
loremRRLRLL_str:	 .asciiz "Excepteur"
loremRRLRLR_str:	 .asciiz "sint"
loremRRLRRL_str:	 .asciiz "occaecat"
loremRRLRRR_str:	 .asciiz "cupidatat"
loremRRRLLL_str:	 .asciiz "non"
loremRRRLLR_str:	 .asciiz "proident,"
loremRRRLRL_str:	 .asciiz "sunt"
loremRRRLRR_str:	 .asciiz "in"
loremRRRRLL_str:	 .asciiz "culpa"
loremRRRRLR_str:	 .asciiz "qui"
loremRRRRRL_str:	 .asciiz "officia"
loremRRRRRR_str:	 .asciiz "deserunt"

