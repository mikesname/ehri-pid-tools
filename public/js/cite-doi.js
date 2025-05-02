
function copyToClipboard(text) {
  navigator.clipboard.writeText(text).then(function () {
    console.log('Copying to clipboard was successful!');
  }, function (err) {
    console.error('Could not copy text: ', err);
  });
}

// Function to update the citation text based on selected format
function getCitationText(cite, format) {
  try {
    switch (format) {
      case 'apa':
        return cite.format('bibliography', {
          format: 'text',
          template: 'apa'
        });
      case 'mla':
        return cite.format('bibliography', {
          format: 'text',
          template: 'mla'
        });
      case 'chicago':
        return cite.format('bibliography', {
          format: 'text',
          template: 'chicago'
        });
      case 'bibtex':
        return cite.format('bibtex');
      case 'ris':
        return cite.format('ris');
      case 'harvard':
        return cite.format('bibliography', {
          format: 'text',
          template: 'harvard1'
        });
    }

    return '';
  } catch (e) {
    console.error('Error formatting citation:', e);
    citationText.text('Error formatting citation');
  }
}

document.onreadystatechange = function () {
  if (document.readyState === "complete") {
    const cite = new Cite(doi);
    const citationText = document.getElementById('citation-text');
    const citeButton = document.getElementById('doi-cite-action');
    const citationFormatSelector = document.getElementById('citation-format-selector');
    const citationFormat = citationFormatSelector.value;
    citationText.textContent = getCitationText(cite, citationFormat);

    // Event listener for format selector change
    citationFormatSelector.addEventListener('change', function () {
      const selectedFormat = citationFormatSelector.value;
      citationText.textContent = getCitationText(cite, selectedFormat);
    });

    citeButton.addEventListener('click', function (event) {
      event.preventDefault();
      const citationCopied = document.querySelector('.citation-copied');
      const copyCitationButton = document.getElementById('copy-citation');
      const citationResult = document.querySelector('.citation-result');
      const citationControls = document.querySelector('.citation-controls');

      citationText.textContent = getCitationText(cite, citationFormat);
    });
  }
};
