import { UTC_OFFSET } from '../timezone';

// Difference in hours between my timezone and the visitor's.
function offsetText(): string {
  const offset = UTC_OFFSET + new Date().getTimezoneOffset() / 60;
  const abs = Math.abs(offset);
  if (offset === 0) return 'Which is the same as yours.';
  if (offset === 1) return 'Which is an hour ahead of you.';
  if (offset === -1) return 'Which is an hour behind you.';
  // Only whole-hour differences get a sentence, like the original.
  if (!Number.isInteger(offset) || abs > 23) return '';
  return `Which is ${abs} hours ${offset > 0 ? 'ahead of' : 'behind'} you.`;
}

function injectTimezoneOffsetInformation() {
  console.log('Adding Timezone Information...');
  const el = document.getElementById('utc-offset');
  if (!el) return;
  el.textContent = offsetText();
  el.style.color = 'black';
}

window.setTimeout(injectTimezoneOffsetInformation, 1000);
