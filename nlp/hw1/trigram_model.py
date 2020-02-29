import sys
from collections import defaultdict
import math
import random
import os
import os.path

import numpy as np
from numpy.random import choice
"""
COMS W4705 - Natural Language Processing - Spring 2020
Homework 1 - Programming Component: Trigram Language Models
Yassine Benajiba
"""

def corpus_reader(corpusfile, lexicon=None): 
    with open(corpusfile,'r') as corpus: 
        for line in corpus: 
            if line.strip():
                sequence = line.lower().strip().split()
                if lexicon: 
                    yield [word if word in lexicon else "UNK" for word in sequence]
                else: 
                    yield sequence

def get_lexicon(corpus):
    word_counts = defaultdict(int)
    for sentence in corpus:
        for word in sentence: 
            word_counts[word] += 1
    return set(word for word in word_counts if word_counts[word] > 1)  


def get_ngrams(sequence, n):
    """
    COMPLETE THIS FUNCTION (PART 1)
    Given a sequence, this function should return a list of n-grams, where each n-gram is a Python tuple.
    This should work for arbitrary values of 1 <= n < len(sequence).
    """
    sequence = ['START']*(1 if n==1 else n-1) + sequence + ['STOP']
    seq_len = len(sequence)
    result = []
    for i in range(seq_len-n+1):
        result.append(tuple(sequence[i:i+n]))
    return result


class TrigramModel(object):
    
    def __init__(self, corpusfile):
    
        # Iterate through the corpus once to build a lexicon 
        generator = corpus_reader(corpusfile)
        self.lexicon = get_lexicon(generator)
        self.lexicon.add("UNK")
        self.lexicon.add("START")
        self.lexicon.add("STOP")
    
        # Now iterate through the corpus again and count ngrams
        generator = corpus_reader(corpusfile, self.lexicon)
        self.count_ngrams(generator)


    def count_ngrams(self, corpus):
        """
        COMPLETE THIS METHOD (PART 2)
        Given a corpus iterator, populate dictionaries of unigram, bigram,
        and trigram counts. 
        """
   
        self.unigramcounts = {} # might want to use defaultdict or Counter instead
        self.bigramcounts = {} 
        self.trigramcounts = {} 

        self.n_words = 0

        ##Your code here
        for sentence in corpus:
            unigram = get_ngrams(sentence, 1)
            bigram = get_ngrams(sentence, 2)
            trigram = get_ngrams(sentence, 3)

            for g in unigram:
                if g not in self.unigramcounts:
                    self.unigramcounts[g] = 1
                else:
                    self.unigramcounts[g] += 1
                self.n_words += 1
            
            for g in bigram:
                if g not in self.bigramcounts:
                    self.bigramcounts[g] = 1
                else:
                    self.bigramcounts[g] += 1

            for g in trigram:
                if g not in self.trigramcounts:
                    self.trigramcounts[g] = 1
                else:
                    self.trigramcounts[g] += 1

        self.bigramcounts[('START', 'START')] = self.unigramcounts[('START',)]
        return

    def raw_trigram_probability(self,trigram):
        """
        COMPLETE THIS METHOD (PART 3)
        Returns the raw (unsmoothed) trigram probability
        """
        return 0.0 if trigram not in self.trigramcounts else self.trigramcounts[trigram]/self.bigramcounts[trigram[:2]]

    def raw_bigram_probability(self, bigram):
        """
        COMPLETE THIS METHOD (PART 3)
        Returns the raw (unsmoothed) bigram probability
        """
        return 0.0 if bigram not in self.bigramcounts else self.bigramcounts[bigram]/self.unigramcounts[bigram[:1]]
    
    def raw_unigram_probability(self, unigram):
        """
        COMPLETE THIS METHOD (PART 3)
        Returns the raw (unsmoothed) unigram probability.
        """

        #hint: recomputing the denominator every time the method is called
        # can be slow! You might want to compute the total number of words once, 
        # store in the TrigramModel instance, and then re-use it.  
        return self.unigramcounts[('UNK',)] if unigram not in self.unigramcounts else self.unigramcounts[unigram]/self.n_words

    def generate_sentence(self,t=20): 
        """
        COMPLETE THIS METHOD (OPTIONAL)
        Generate a random sentence from the trigram model. t specifies the
        max length, but the sentence may be shorter if STOP is reached.
        """
        result = []
        cur = ('START', 'START')
        c = 0
        while cur[1] != 'STOP' and c < t:
            filtered_tris = []
            filtered_tris_p = []
            cur_p = self.raw_bigram_probability(cur)
            for key in self.trigramcounts:
                if key[:2] == cur:
                    filtered_tris.append(key[2])
                    filtered_tris_p.append(self.raw_trigram_probability(key))
            filtered_tris_p = np.array(filtered_tris_p)
            filtered_tris_p /= filtered_tris_p.sum()
            draw = choice(filtered_tris, 1, p=filtered_tris_p)[0]
            result.append(draw)
            cur = (cur[1], draw)
            c += 1
        return result            

    def smoothed_trigram_probability(self, trigram):
        """
        COMPLETE THIS METHOD (PART 4)
        Returns the smoothed trigram probability (using linear interpolation). 
        """

        lambda1 = 1/3.0
        lambda2 = 1/3.0
        lambda3 = 1/3.0

        p = lambda1*self.raw_trigram_probability(trigram)
        p += lambda2*self.raw_bigram_probability(trigram[1:]) 
        p += lambda3*self.raw_unigram_probability(trigram[2:])
        return p
        
    def sentence_logprob(self, sentence):
        """
        COMPLETE THIS METHOD (PART 5)
        Returns the log probability of an entire sequence.
        """
        res = 0
        for g in get_ngrams(sentence, 3):
            p = self.smoothed_trigram_probability(g)
            res += math.log2(p)
        return res

    def perplexity(self, corpus):
        """
        COMPLETE THIS METHOD (PART 6) 
        Returns the log probability of an entire sequence.
        """
        res = 0
        n_words = 0
        for sentence in corpus:
            res += self.sentence_logprob(sentence)
            n_words += 2+len(sentence)
        res /= n_words
        return 2**(-res) 


def essay_scoring_experiment(training_file1, training_file2, testdir1, testdir2):

        model1 = TrigramModel(training_file1)
        model2 = TrigramModel(training_file2)

        total = 0
        correct = 0       
 
        for f in os.listdir(testdir1):
            # this model is supposed to have lower perplexity
            pp = model1.perplexity(corpus_reader(os.path.join(testdir1, f), model1.lexicon))
            # this model is supposed to have higher perplexity
            np = model2.perplexity(corpus_reader(os.path.join(testdir1, f), model2.lexicon))
            total += 1
            if pp <= np:
                correct += 1
    
        for f in os.listdir(testdir2):
            pp = model2.perplexity(corpus_reader(os.path.join(testdir2, f), model2.lexicon))
            np = model1.perplexity(corpus_reader(os.path.join(testdir2, f), model1.lexicon))
            total += 1
            if pp <= np:
                correct += 1
        
        return correct/total

if __name__ == "__main__":

    model = TrigramModel("brown_train.txt") 
    
    #Testing perplexity: 
    dev_corpus = corpus_reader("brown_test.txt", model.lexicon)
    pp = model.perplexity(dev_corpus)
    print('perplexity on test', pp)

    corpus = corpus_reader("brown_train.txt", model.lexicon)
    pp = model.perplexity(corpus)
    print('perplexity on train', pp)

    #Essay scoring experiment: 
    acc = essay_scoring_experiment("train_high.txt", "train_low.txt", "test_high", "test_low")
    print(acc)

