import numpy as np
import sys
import pandas as pd
import copy


def LD_to_DL(LD):
     return dict(zip(LD[0],zip(*[d.values() for d in LD])))

def zo(x):
    return np.minimum(np.maximum(0., x), 1.)

""" I/O Conversion Functions
    --------
    The following functions handle the conversion of numpy arrays into lists and back. 
    This behavior is useful if you are going to be primarily using numpy in your code.
    For behavior consistent with the docstring replace these with identity functions 
    as shown.
"""
def get_columns(x):
    return [column.T for column in x.T]
    # return x # For default docstring behavior

def return_columns(x):
    return np.vstack(x).T
    # return x # For default docstring behavior

""" State and Input Name Dictionaries
    --------
    The following dictionaries are helpful for building a simulator and keeping track of the ordering of the variables.
"""

SN = {} # State name dictionary
SN['HH_step'] = ['V', 'n', 'm', 'h', 'spike', 'Vprev1', 'Vprev2']
SN['CS_step'] = ['V', 'n', 'm', 'h', 'a', 'b', 'spike', 'Vprev1', 'Vprev2']
SN['ML_step'] = ['V', 'R', 'spike', 'Vprev1', 'Vprev2']
SN['IonotropicSynapse_step'] = ['g_syn', 'x1']
SN['MetabotropicSynapse_step'] = ['g_syn', 'x1', 'x2']
SN['Dendrite_step'] = ['I']
SN['ConstantCurrent_step'] = ['I']

IN = {} # Input name dictionary
IN['HH_step'] = ['I']
IN['CS_step'] = ['I']
IN['ML_step'] = ['I']
IN['IonotropicSynapse_step'] = ['spike']
IN['MetabotropicSynapse_step'] = ['spike']
IN['Dendrite_step'] = ['g_syn', 'V']
IN['ConstantCurrent_step'] = ['I']

""" Model Step Functions
    --------
    Step functions for various models.
"""

def HH_step(dt, I, x):
    """Step function for a Hodgkin-Huxley neuron

    Parameters
    --------
    dt: float
        Time step in seconds
    I: float
        Injected current
    x: list
        State list, containing:
        V: float
            Current voltage
        n: float
            Current state variable value
        m: float
            Current state variable value
        h: float
            Current state variable value
        spike: float
            Current binary spike state
        Vprev1: float
            Voltage 1 time step ago
        Vprev2: float
            Voltage 2 time steps ago

    Returns
    -------
    x: list
        Updated state list.
    """
    
    dt = dt * 1000.
    I = get_columns(I)[0]
    V, n, m, h, spike, Vprev1, Vprev2 = get_columns(x)

    n = zo(n)
    m = zo(m)
    h = zo(h)
    
    E_K = -77
    E_Na = 50
    E_L = -54.387
    gmax_K = 36
    gmax_Na = 120
    g_L = 0.3
    
    an = 0.01*(V+55)/(1-np.exp(-0.1*(V+55)))
    bn = 0.125*np.exp(-(V+65)/80)
    

    am = 0.1*(V+40)/(1-np.exp(-0.1*(V+40)))
    bm = 4*np.exp(-(V+65)/18)
    
    
    ah = 0.07*np.exp(-0.05*(V+65))
    bh = 1/(1+np.exp(-0.1*(V+35)))
    
    # Update internal state variables
    dn = (an*(1.-n)-bn*n)
    dm = (am*(1.-m)-bm*m)
    dh = (ah*(1.-h)-bh*h)
    dm[np.isnan(dm)] = 0.
    dn[np.isnan(dn)] = 0.
    dh[np.isnan(dh)] = 0.
    
    # Calculate the memconductances
    g_K = gmax_K*(n**4.)
    g_Na = gmax_Na*(m**3.)*h
    
    # Update the ionic currents: and membrane voltage:
    I_K = g_K*(V-E_K)
    I_Na = g_Na*(V-E_Na)
    I_L = g_L*(V-E_L)
    
    # Calculate the gradients
    dV = I-I_K-I_Na-I_L
    dV[np.isnan(dV)] = 0.
    spike = (Vprev2<=Vprev1) * (Vprev1 >= V) * (Vprev1 > -30)

    x = [V+dt*dV, n+dt*dn, m+dt*dm, h+dt*dh, spike, V+dt*dV, Vprev1]

    return return_columns(x)


def ConstantCurrent_step(dt, I, x, value = 0.0):
    """Step function for a constant current injector

    Parameters
    --------
    dt: float
        Time step in seconds
    I: empty list
    x: empty list

    value: float
        Constant value

    Returns
    -------
    x: list
        Updated state list.
    """
    I = get_columns(I)[0]
    x = [value + I]
    
    return return_columns(x)
